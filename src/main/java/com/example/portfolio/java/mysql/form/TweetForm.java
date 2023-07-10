package com.example.portfolio.java.mysql.form;

import java.util.Objects;

public class TweetForm {
    private String tweet;
    private int userId;

    public TweetForm(String tweet, int userId) {
        this.tweet = tweet;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TweetForm tweetForm = (TweetForm) o;
        return userId == tweetForm.userId && Objects.equals(tweet, tweetForm.tweet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tweet, userId);
    }

    public String getTweet() {
        return tweet;
    }

    public int getUserId() {
        return userId;
    }
}
