package com.lineup.application.token.in;

import com.lineup.application.exception.DuplicateTokenException;
import com.lineup.domain.model.Token;

public interface TokenUseCase {

    Token generateToken(String userId, String queueId) throws DuplicateTokenException;
}
