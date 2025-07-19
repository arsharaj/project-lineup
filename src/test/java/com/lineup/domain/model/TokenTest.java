package com.lineup.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

class TokenTest {

    @Test
    void tokenProperties() {
        Token token = new Token("user", "test", 5, 5);

        assertThat(token.getId()).isNotBlank();
        assertThat(token.getUserId()).isEqualTo("user");
        assertThat(token.getQueueId()).isEqualTo("test");
        assertThat(token.getPosition()).isEqualTo(5);
        assertThat(token.getServiceTime()).isEqualTo(5);
        assertThat(token.getWaitTime()).isGreaterThanOrEqualTo(5);
        assertThat(token.getTimestamp()).isLessThanOrEqualTo(LocalDateTime.now().toString());
    }
}