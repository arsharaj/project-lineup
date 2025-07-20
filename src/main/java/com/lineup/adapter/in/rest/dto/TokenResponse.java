package com.lineup.adapter.in.rest.dto;

import com.lineup.domain.model.Token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {

    private String tokenId;
    private String userId;
    private String queueId;
    private int position;
    private int serviceTime;
    private int waitTime;
    private String timestamp;
    private String tokenStatus;
    private String servingCounterId;
    private String errorMessage;

    public TokenResponse(Token token) {
        if (token != null) {
            this.tokenId = token.getId();
            this.userId = token.getUserId();
            this.queueId = token.getQueueId();
            this.position = token.getPosition();
            this.serviceTime = token.getServiceTime();
            this.waitTime = token.getWaitTime();
            this.timestamp = token.getTimestamp();
            this.tokenStatus = token.getStatus().toString();
            this.servingCounterId = token.getServingCounterId();
        }
    }

    public TokenResponse(Token token, String errorMessage) {
        this(token);
        this.errorMessage = errorMessage;
    }
}
