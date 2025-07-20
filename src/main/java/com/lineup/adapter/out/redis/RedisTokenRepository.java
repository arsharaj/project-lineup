package com.lineup.adapter.out.redis;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import com.lineup.application.token.out.TokenRepository;
import com.lineup.domain.model.Token;
import com.lineup.domain.model.TokenStatus;

@Repository
public class RedisTokenRepository implements TokenRepository {

    private static final Duration TOKEN_DURATION = Duration.ofHours(6);

    private final ValueOperations<String, Token> tokenValueOps;
    private final ValueOperations<String, Integer> counterValueOps;
    private final ZSetOperations<String, String> waitingZsetOps;

    public RedisTokenRepository(
            RedisTemplate<String, Token> tokenRedisTemplate,
            RedisTemplate<String, Integer> integerRedisTemplate,
            RedisTemplate<String, String> stringRedisTemplate) {
        this.tokenValueOps = tokenRedisTemplate.opsForValue();
        this.counterValueOps = integerRedisTemplate.opsForValue();
        this.waitingZsetOps = stringRedisTemplate.opsForZSet();
    }

    @Override
    public void save(Token token) {
        String tokenKey = RedisKeyTemplate.tokenKey(token.getId());
        String userQueueKey = RedisKeyTemplate.userQueueKey(token.getUserId(), token.getQueueId());
        String queueCounterKey = RedisKeyTemplate.queueCounterKey(token.getQueueId());
        String waitingQueueKey = RedisKeyTemplate.waitingQueueKey(token.getQueueId());
        // Save the token by its unique token id
        tokenValueOps.set(tokenKey, token, TOKEN_DURATION);
        // Save user to queue mapping for duplicate check
        tokenValueOps.set(userQueueKey, token, TOKEN_DURATION);
        // Save queue position counter
        counterValueOps.setIfAbsent(queueCounterKey, token.getPosition());
        // Add the token to respective waiting queue
        waitingZsetOps.add(waitingQueueKey, token.getId(), token.getPosition());
    }

    @Override
    public Optional<Token> getToken(String tokenId) {
        String tokenKey = RedisKeyTemplate.tokenKey(tokenId);
        Token token = tokenValueOps.get(tokenKey);
        if (token == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(token);

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

    @Override
    public Token findNextWaitingToken(String queueId) {
        String waitingQueueKey = RedisKeyTemplate.waitingQueueKey(queueId);
        Set<String> tokenIds = waitingZsetOps.range(waitingQueueKey, 0, 0);
        if (tokenIds == null || tokenIds.isEmpty()) {
            return null;
        }
        String tokenKey = RedisKeyTemplate.tokenKey(tokenIds.iterator().next());
        return tokenValueOps.get(tokenKey);
    }

    @Override
    public void updateTokenStatus(String tokenId, TokenStatus status) {
        String tokenKey = RedisKeyTemplate.tokenKey(tokenId);
        Token token = tokenValueOps.get(tokenKey);
        if (token != null) {
            token.setStatus(status);
            tokenValueOps.set(tokenKey, token);
        }
    }

}
