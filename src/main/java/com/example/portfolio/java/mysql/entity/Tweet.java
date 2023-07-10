package com.example.portfolio.java.mysql.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Tweet {
    private int id;
    private String tweet;
    private LocalDateTime createdAt;
    private int userId;

    public Tweet(int id, String tweet, LocalDateTime createdAt, int userId) {
        this.id = id;
        this.tweet = tweet;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public Tweet(String tweet, int userId) {
        this.tweet = tweet;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public String getTweet() {
        return tweet;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweet1 = (Tweet) o;
        if (id != tweet1.id) return false;
        if (userId != tweet1.userId) return false;
        if (!Objects.equals(tweet, tweet1.tweet)) return false;
        return Objects.equals(createdAt, tweet1.createdAt);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (tweet != null ? tweet.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + userId;
        return result;
    }
}
