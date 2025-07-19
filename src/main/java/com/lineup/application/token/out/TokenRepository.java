package com.lineup.application.token.out;

import java.util.Optional;

import com.lineup.domain.model.Token;

public interface TokenRepository {

    void save(Token token);

    int getNextPosition(String queueId);

    Optional<Token> findRecentByUserAndQueue(String userId, String queueId);
}