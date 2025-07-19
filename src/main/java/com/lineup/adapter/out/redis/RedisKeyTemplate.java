package com.lineup.adapter.out.redis;

public final class RedisKeyTemplate {

    private RedisKeyTemplate() {
        // Disable instantiation
    }

    private static final String TOKEN_KEY = "token:%s";
    private static final String USER_QUEUE_KEY = "user:%s:queue:%s";
    private static final String QUEUE_COUNTER_KEY = "queue:%s:counter";

    public static String tokenKey(String tokenId) {
        return String.format(TOKEN_KEY, tokenId);
    }

    public static String userQueueKey(String userId, String queueId) {
        return String.format(USER_QUEUE_KEY, userId, queueId);
    }

    public static String queueCounterKey(String queueId) {
        return String.format(QUEUE_COUNTER_KEY, queueId);
    }
}
