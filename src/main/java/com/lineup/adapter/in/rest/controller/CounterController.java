package com.lineup.adapter.in.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lineup.adapter.in.rest.dto.CounterRequest;
import com.lineup.adapter.in.rest.dto.TokenResponse;
import com.lineup.application.token.service.CounterService;
import com.lineup.domain.model.Token;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/counters")
@RequiredArgsConstructor
public class CounterController {

    private final CounterService counterService;

    @GetMapping("/next")
    public ResponseEntity<TokenResponse> getNextToken(@Valid @RequestBody CounterRequest counterRequest) {
        Token token = counterService.getNextTokenForCounter(counterRequest.getCounterId());
        if (token == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new TokenResponse(token));
        }

    }

    @PutMapping("/service")
    public ResponseEntity<String> markTokenInService(@RequestBody CounterRequest counterRequest) {
        if (counterService.markTokenAsServing(counterRequest.getTokenId(), counterRequest.getCounterId())) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Token marked as serving.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Token cannot be marked as serving.");
        }
    }

}
