package com.example.portfolio.java.mysql.mapper;

import com.example.portfolio.java.mysql.controller.TweetResponse;
import com.example.portfolio.java.mysql.entity.Tweet;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TweetMapperTest {
    @Autowired
    TweetMapper tweetMapper;

    // テストケースの前処理、外部キー制約を無効にする。
    @BeforeAll
    public static void initializeDb(@Autowired DataSource dataSource){
        ResourceDatabasePopulator connDb = new ResourceDatabasePopulator();
        connDb.addScript(new ClassPathResource("foreignKeyChecksFalse.sql"));
        connDb.execute(dataSource);
    }

    // テストケースの後処理、外部キー制約を有効にする。
    @AfterAll
    public static void finalizeDb(@Autowired DataSource dataSource){
        ResourceDatabasePopulator connDb = new ResourceDatabasePopulator();
        connDb.addScript(new ClassPathResource("foreignKeyChecksTrue.sql"));
        connDb.execute(dataSource);
    }

    @Test
    @DataSet(value = "tweet/tweetList.yml")
    @Transactional
    public void ツイートの一覧が取得出来ること() {
        List<TweetResponse> tweetResponses = tweetMapper.getTweets();
        LocalDateTime runDateTime = LocalDateTime.now();
        List<TweetResponse> expectResponses = List.of(
            new TweetResponse( 1, "ツイート1", runDateTime, 1, "k_kotomi"),
            new TweetResponse( 2, "ツイート2", runDateTime, 3, "y_moriyama"),
            new TweetResponse( 3, "ツイート3", runDateTime, 2, "k_kawai")
        );
        for (int i = 0; i < tweetResponses.size(); i++) {
            assertThat(tweetResponses.get(i).getId()).isEqualTo(expectResponses.get(i).getId());
            assertThat(tweetResponses.get(i).getTweet()).isEqualTo(expectResponses.get(i).getTweet());
            assertThat(tweetResponses.get(i).getAuthorId()).isEqualTo(expectResponses.get(i).getAuthorId());
            assertThat(tweetResponses.get(i).getAuthorName()).isEqualTo(expectResponses.get(i).getAuthorName());
        }
    }

    @Test
    @DataSet(value = "tweet/tweetEmpty.yml")
    @Transactional
    public void ツイートが存在しない時は空のListを返すこと() {
        List<TweetResponse> tweetResponses = tweetMapper.getTweets();
        assertThat(tweetResponses).isEmpty();
    }

    @Test
    @DataSet(value = "tweet/tweetList.yml")
    @Transactional
    @ExpectedDataSet(value = "tweet/tweetCreateList.yml", ignoreCols = {"id", "created_at"})
    public void ツイート出来ることその際にオートインクリメントされること(){
        // 作成処理の前後でidの有無を確かめる。
        Tweet tweet4 = new Tweet("ツイート4", 1);
        assertThat(tweet4.getId()).isEqualTo(0);
        tweetMapper.tweetRegister(tweet4);
        assertThat(tweet4.getId()).isGreaterThan(0);

        Tweet tweet5 = new Tweet("ツイート5", 3);
        assertThat(tweet5.getId()).isEqualTo(0);
        tweetMapper.tweetRegister(tweet5);
        assertThat(tweet5.getId()).isGreaterThan(0);

        // idがオートインクリメントされていることを確認する。
        // 後で作ったtweet5のidの方が大きいことを確認する。
        assertThat(tweet5.getId()).isGreaterThan(tweet4.getId());
    }
}
