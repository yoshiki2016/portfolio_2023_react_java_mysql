package com.example.portfolio.java.mysql.service;

import com.example.portfolio.java.mysql.controller.TweetResponse;
import com.example.portfolio.java.mysql.entity.Tweet;

import java.util.List;

public interface TweetService {
    List<TweetResponse> getTweets();

    Tweet tweetRegister(String tweet, int userId);
}
