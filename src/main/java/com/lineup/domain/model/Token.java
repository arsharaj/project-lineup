package com.lineup.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Token implements Serializable {

    private String id;
    private String userId;
    private String queueId;
    private int position;
    private int serviceTime;
    private int waitTime;
    private String timestamp;
    @Setter
    private TokenStatus status;
    @Setter
    private String servingCounterId;

    public Token(
            String userId,
            String queueId,
            int position,
            int serviceTime) {
        this.id = generateId();
        this.userId = userId;
        this.queueId = queueId;
        this.position = position;
        this.serviceTime = serviceTime;
        this.waitTime = calculateWaitTime();
        this.timestamp = generateTimeStamp();
        this.status = TokenStatus.WAITING;
    }

    private String generateId() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));
    }

    private String generateTimeStamp() {
        return LocalDateTime.now().toString();
    }

    private int calculateWaitTime() {
        return position * serviceTime;
    }
}