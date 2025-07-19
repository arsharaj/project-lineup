package com.lineup.application.token.service;

import org.springframework.stereotype.Component;

import com.lineup.application.token.out.TokenRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TokenValidator {

    private TokenRepository tokenRepository;

    public boolean hasRecentToken(String userId, String queueId) {
        return tokenRepository.findRecentByUserAndQueue(userId, queueId).isPresent();
    }

}
