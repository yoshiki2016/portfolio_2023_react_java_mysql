package com.example.portfolio.java.mysql.controller;

import com.example.portfolio.java.mysql.entity.Tweet;
import com.example.portfolio.java.mysql.form.TweetForm;
import com.example.portfolio.java.mysql.form.UserForm;
import com.example.portfolio.java.mysql.service.TweetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RestController
public class TweetController {

    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping("/tweets")
    public List<TweetResponse> getTweets() {
        return tweetService.getTweets();
    }

    @PostMapping("/tweet/register")
    public ResponseEntity<Map<String, String>> tweetRegister(@RequestBody TweetForm tweetForm, UriComponentsBuilder uriBuilder) {
        Tweet tweet = tweetService.tweetRegister(tweetForm.getTweet(), tweetForm.getUserId());
        URI url = uriBuilder
                .path("/tweet/" + tweet.getId())
                .build()
                .toUri();
        return ResponseEntity.created(url).body(Map.of("message", "the tweet successfully created"));
    }
}
