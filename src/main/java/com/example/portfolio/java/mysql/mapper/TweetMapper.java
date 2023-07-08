package com.example.portfolio.java.mysql.mapper;

import com.example.portfolio.java.mysql.entity.Tweet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TweetMapper {
    public List<Tweet> findTweets();
}
