package com.example.portfolio.java.mysql.integrationtest;

import com.example.portfolio.java.mysql.form.LoginForm;
import com.example.portfolio.java.mysql.form.UserForm;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
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

import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@DBRider
public class UserRestApiIntegrationTest {
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
    @DataSet(value = "user/userList.yml")
    @Transactional
    void usersテーブルに存在するユーザー名とパスワードでログイン出来ること() throws Exception {
        LoginForm loginForm = new LoginForm("k_kawai", "W-DSVjma");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestBody = ow.writeValueAsString(loginForm);
        String response = mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                {
                    "userId": 2,
                    "loginFlag": true
                }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "user/userList.yml")
    @Transactional
    void usersテーブルに存在しないユーザー名とパスワードでログイン出来ないこと() throws Exception {
        LoginForm loginForm = new LoginForm("w_asaka", "Sr0LVDwX");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestBody = ow.writeValueAsString(loginForm);
        String response = mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                {
                    "userId": null,
                    "loginFlag": false
                }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "user/userList.yml")
    @ExpectedDataSet(value = "user/userCreateList.yml", ignoreCols = "id")
    @Transactional
    void ユーザー登録が出来ること() throws Exception {
        UserForm userForm1 = new UserForm("秀加", "山内",
                "y_hideka", "nm_8BrTv", "hideka28544@epjtdsmc.vyt.edk");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestBody1 = ow.writeValueAsString(userForm1);
        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody1))
                .andExpect(status().isCreated()) // status が201であること
                .andExpect(header().string("Location", matchesPattern("http://localhost/user/\\d+")));
        UserForm userForm2 = new UserForm("涼", "杉田", "s_ryo",
                "FLemJxow", "uqcztuhfqxryou86581@jgyecl.uck");
        String requestBody2 = ow.writeValueAsString(userForm2);
        String response = mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody2))
                .andExpect(status().isCreated()) // status が201であること
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                {
                    "message" : "the user successfully created"
                }
                """, response, JSONCompareMode.STRICT);
    }

}
