package com.example.portfolio.java.mysql.controller;

import java.time.LocalDateTime;
import java.util.Objects;

public class TweetResponse {
    private int id;
    private String tweet;
    private LocalDateTime createdAt;
    private int authorId;
    private String authorName;

    public TweetResponse(int id, String tweet, LocalDateTime createdAt, int authorId, String authorName) {
        this.id = id;
        this.tweet = tweet;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.authorName = authorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TweetResponse that = (TweetResponse) o;
        return id == that.id && authorId == that.authorId && Objects.equals(tweet, that.tweet) && Objects.equals(createdAt, that.createdAt) && Objects.equals(authorName, that.authorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tweet, createdAt, authorId, authorName);
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

    public int getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }
}
