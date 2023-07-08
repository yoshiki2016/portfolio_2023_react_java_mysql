package com.example.portfolio.java.mysql.controller;

import com.example.portfolio.java.mysql.entity.Tweet;
import com.example.portfolio.java.mysql.service.TweetService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
public class TweetController {

    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping("/tweets")
    public List<Tweet> findUsers() {
        System.out.println("Tweets Read API呼び出し");
        return tweetService.findTweets();
    }
}
