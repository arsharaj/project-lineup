package com.lineup.adapter.in.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lineup.adapter.in.rest.dto.TokenRequest;
import com.lineup.adapter.in.rest.dto.TokenResponse;
import com.lineup.application.exception.DuplicateTokenException;
import com.lineup.application.token.in.TokenUseCase;
import com.lineup.domain.model.Token;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tokens")
@RequiredArgsConstructor
public class TokenController {

    private final TokenUseCase tokenUseCase;

    @PostMapping("/generate")
    public ResponseEntity<TokenResponse> generateToken(@Valid @RequestBody TokenRequest tokenRequest) {
        try {
            Token token = tokenUseCase.generateToken(tokenRequest.getUserId(), tokenRequest.getQueueId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new TokenResponse(token));
        } catch (DuplicateTokenException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new TokenResponse(null, ex.getMessage()));
        }
    }
}
