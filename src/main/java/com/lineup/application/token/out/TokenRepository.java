package com.lineup.application.token.out;

import java.util.Optional;

import com.lineup.domain.model.Token;
import com.lineup.domain.model.TokenStatus;

public interface TokenRepository {

    void save(Token token);

    Optional<Token> getToken(String tokenId);

    int getNextPosition(String queueId);

    Optional<Token> findRecentByUserAndQueue(String userId, String queueId);

    Token findNextWaitingToken(String queueId);

    void updateTokenStatus(String tokenId, TokenStatus status);
}