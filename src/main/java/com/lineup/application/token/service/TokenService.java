package com.lineup.application.token.service;

import org.springframework.stereotype.Service;

import com.lineup.application.exception.DuplicateTokenException;
import com.lineup.application.token.in.TokenUseCase;
import com.lineup.application.token.out.TokenRepository;
import com.lineup.domain.model.Token;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TokenService implements TokenUseCase {

    private final TokenRepository tokenRepository;
    private final TokenValidator duplicateTokenValidator;

    @Override
    public Token generateToken(String userId, String queueId) throws DuplicateTokenException {
        if (duplicateTokenValidator.hasRecentToken(userId, queueId)) {
            throw new DuplicateTokenException("Token already generated for this user.");
        }

        int position = tokenRepository.getNextPosition(queueId);
        int serviceTime = 15; // Average token service time

        Token token = new Token(userId, queueId, position, serviceTime);
        tokenRepository.save(token);

        return token;
    }

}
