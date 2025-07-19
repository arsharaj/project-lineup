package com.lineup.adapter.out.redis;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.lineup.application.token.out.TokenRepository;
import com.lineup.domain.model.Token;

@Repository
public class RedisTokenRepository implements TokenRepository {

    private static final Duration TOKEN_DURATION = Duration.ofHours(6);

    private final ValueOperations<String, Token> tokenValueOps;
    private final ValueOperations<String, Integer> counterValueOps;

    public RedisTokenRepository(
            RedisTemplate<String, Token> tokenRedisTemplate,
            RedisTemplate<String, Integer> counterRedisTemplate) {
        this.tokenValueOps = tokenRedisTemplate.opsForValue();
        this.counterValueOps = counterRedisTemplate.opsForValue();
    }

    @Override
    public void save(Token token) {
        String tokenKey = RedisKeyTemplate.tokenKey(token.getId());
        String userQueueKey = RedisKeyTemplate.userQueueKey(token.getUserId(), token.getQueueId());
        String queueCounterKey = RedisKeyTemplate.queueCounterKey(token.getQueueId());
        // Save the token by its unique token id
        tokenValueOps.set(tokenKey, token, TOKEN_DURATION);
        // Save user to queue mapping for duplicate check
        tokenValueOps.set(userQueueKey, token, TOKEN_DURATION);
        // Save queue position counter
        counterValueOps.setIfAbsent(queueCounterKey, token.getPosition());
    }

    @Override
    public int getNextPosition(String queueId) {
        Long position = counterValueOps.increment(RedisKeyTemplate.queueCounterKey(queueId));
        return position != null ? position.intValue() : 1;
    }

    @Override
    public Optional<Token> findRecentByUserAndQueue(String userId, String queueId) {
        Token token = tokenValueOps.get(RedisKeyTemplate.userQueueKey(userId, queueId));
        return Optional.ofNullable(token);
    }

}
