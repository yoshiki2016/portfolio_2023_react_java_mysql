package com.example.portfolio.java.mysql.service;

import com.example.portfolio.java.mysql.entity.Tweet;

import java.util.List;

public interface TweetService {
    List<Tweet> findTweets();

    Tweet tweetRegister(String tweet, int userId);
}
