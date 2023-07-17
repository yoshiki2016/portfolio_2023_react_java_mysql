package com.example.portfolio.java.mysql.service;

import com.example.portfolio.java.mysql.controller.TweetResponse;
import com.example.portfolio.java.mysql.entity.Tweet;
import com.example.portfolio.java.mysql.mapper.TweetMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TweetServiceImplTest {
    @InjectMocks
    TweetServiceImpl tweetServiceImpl;

    @Mock
    TweetMapper tweetMapper;

    @Test
    public void ツイートが取得出来ること() {
        LocalDateTime runDateTime = LocalDateTime.now();
        List<TweetResponse> tweetResponses = List.of(
                new TweetResponse( 1, "ツイート1", runDateTime, 1, "k_kotomi"),
                new TweetResponse( 2, "ツイート2", runDateTime, 3, "y_moriyama"),
                new TweetResponse( 3, "ツイート3", runDateTime, 2, "k_kawai")
        );
        Mockito.doReturn(tweetResponses).when(tweetMapper).getTweets();
        List<TweetResponse> actual = tweetServiceImpl.getTweets();
        assertThat(actual).isEqualTo(tweetResponses);
    }

    @Test
    public void ツイートを登録出来ること() {
        Tweet testTweet = new Tweet("ツイート1", 1);
        Mockito.doNothing().when(tweetMapper).tweetRegister(testTweet);
        tweetServiceImpl.tweetRegister("ツイート1", 1);
        verify(tweetMapper).tweetRegister(testTweet);
    }
}
