package com.lineup.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import lombok.Getter;
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