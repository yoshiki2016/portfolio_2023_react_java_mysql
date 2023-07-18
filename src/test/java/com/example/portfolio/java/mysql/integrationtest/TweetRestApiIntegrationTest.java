package com.example.portfolio.java.mysql.integrationtest;

import com.example.portfolio.java.mysql.controller.TweetResponse;
import com.example.portfolio.java.mysql.form.TweetForm;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.type.TypeReference;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DBRider
public class TweetRestApiIntegrationTest {
    @Autowired
    MockMvc mockMvc;

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
    public void ツイートの一覧を取得出来ること() throws Exception {
        // Stringでリクエストボディー取得
        String response = mockMvc.perform(get("/tweets"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        // ObjectMapperの登場
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Stringで取得したリクエストボディーをレスポンスクラスのリストに変換
            List<TweetResponse> tweetResponses = mapper.readValue(response, new TypeReference<>() {});
            LocalDateTime runDateTime = LocalDateTime.now();
            List<TweetResponse> expectResponses = List.of(
                    new TweetResponse( 1, "ツイート1", runDateTime, 1, "k_kotomi"),
                    new TweetResponse( 2, "ツイート2", runDateTime, 3, "y_moriyama"),
                    new TweetResponse( 3, "ツイート3", runDateTime, 2, "k_kawai")
            );
            // 上記リストを期待値と突合
            for (int i = 0; i < tweetResponses.size(); i++) {
                assertThat(tweetResponses.get(i).getId()).isEqualTo(expectResponses.get(i).getId());
                assertThat(tweetResponses.get(i).getTweet()).isEqualTo(expectResponses.get(i).getTweet());
                assertThat(tweetResponses.get(i).getAuthorId()).isEqualTo(expectResponses.get(i).getAuthorId());
                assertThat(tweetResponses.get(i).getAuthorName()).isEqualTo(expectResponses.get(i).getAuthorName());
            }
        } catch (Exception e) {
            new Exception("モデルに変換出来ませんでした。");
        }
    }


    @Test
    @DataSet(value = "tweet/tweetList.yml")
    @Transactional
    @ExpectedDataSet(value = "tweet/tweetCreateList.yml", ignoreCols = {"id", "created_at"})
    public void ツイートを投稿出来ること() throws Exception {
        TweetForm tweetForm4 = new TweetForm("ツイート4", 1);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestBody4 = ow.writeValueAsString(tweetForm4);
        mockMvc.perform(post("/tweet/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody4))
                .andExpect(status().isCreated()) // status codeが201であること
                .andExpect(header().string("Location", matchesPattern("http://localhost/tweet/\\d+")));

        TweetForm tweetForm5 = new TweetForm("ツイート5", 3);
        String requestBody5 = ow.writeValueAsString(tweetForm5);
        String response = mockMvc.perform(post("/tweet/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody5))
                .andExpect(status().isCreated()) // status codeが201であること
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                    "message" : "the tweet successfully created"
                }
                """, response, JSONCompareMode.STRICT);
    }
}
