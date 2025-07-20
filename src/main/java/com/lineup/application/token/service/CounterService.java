package com.lineup.application.token.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lineup.application.token.out.TokenRepository;
import com.lineup.domain.model.Token;
import com.lineup.domain.model.TokenStatus;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CounterService {

    private static final Map<String, String> COUNTER_QUEUE_MAP = new HashMap<>();

    static {
        COUNTER_QUEUE_MAP.put("test", "test");
    }

    private final TokenRepository tokenRepository;

    public Token getNextTokenForCounter(String counterId) {
        String queueId = COUNTER_QUEUE_MAP.get(counterId);
        if (queueId == null) {
            return null;
        } else {
            return tokenRepository.findNextWaitingToken(queueId);
        }
    }

    public boolean markTokenAsServing(String tokenId, String counterId) {
        Token token = tokenRepository.getToken(tokenId).orElse(null);
        if (token != null && token.getStatus() == TokenStatus.WAITING) {
            token.setServingCounterId(counterId);
            tokenRepository.save(token);
            tokenRepository.updateTokenStatus(tokenId, TokenStatus.SERVING);
            return true;
        }
        return false;
    }
}
