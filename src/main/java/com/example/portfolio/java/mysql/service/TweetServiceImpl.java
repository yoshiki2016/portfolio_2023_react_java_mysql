package com.example.portfolio.java.mysql.service;

import com.example.portfolio.java.mysql.entity.Tweet;
import com.example.portfolio.java.mysql.form.TweetForm;
import com.example.portfolio.java.mysql.mapper.TweetMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetServiceImpl implements TweetService {
    private final TweetMapper tweetMapper;

    public TweetServiceImpl(TweetMapper tweetMapper) {
        this.tweetMapper = tweetMapper;
    }

    @Override
    public List<Tweet> findTweets() {
        return tweetMapper.findTweets();
    }

    public Tweet tweetRegister(String tweet, int userId){
        Tweet tweetTarget = new Tweet(tweet, userId);
        tweetMapper.tweetRegister(tweetTarget);
        return tweetTarget;
    }
}
