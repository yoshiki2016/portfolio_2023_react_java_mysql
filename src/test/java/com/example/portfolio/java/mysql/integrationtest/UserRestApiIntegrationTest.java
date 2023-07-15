package com.example.portfolio.java.mysql.integrationtest;

import com.example.portfolio.java.mysql.form.LoginForm;
import com.example.portfolio.java.mysql.form.UserForm;
import com.example.portfolio.java.mysql.form.UserUpdateForm;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
                    "userId": 0,
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

    @Test
    @DataSet(value = "user/userList.yml")
    @ExpectedDataSet(value = "user/userUpdateList.yml")
    @Transactional
    void ユーザーをパスワードありとなしで更新出来ること() throws Exception {
        // パスワードを除いて更新出来ること
        UserUpdateForm userUpdateForm1 = new UserUpdateForm(1,"琴美2", "田中", "t_kotomi2",
                false, "", "", "abc@example.com");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestBody1 = ow.writeValueAsString(userUpdateForm1);
        String response1 = mockMvc.perform(patch("/user_setting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody1))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                {
                    "message" : "the user successfully updated"
                }
                """, response1, JSONCompareMode.STRICT);

        // パスワードを含んで更新が出来ること
        UserUpdateForm userUpdateForm2 = new UserUpdateForm(2,"港一3", "河合", "k_kawai3",
                true, "W-DSVjma", "P@ssword1!", "def@example.com");
        String requestBody2 = ow.writeValueAsString(userUpdateForm2);
        String response2 = mockMvc.perform(patch("/user_setting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody2))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                {
                    "message" : "the user successfully updated"
                }
                """, response2, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "user/userList.yml")
    @Transactional
    void 存在しないUserのIDとパスワードのペアを検知したらExceptionすること() throws Exception {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2023, 7, 15, 20, 0, 0, 0, ZoneId.of("Asia/Tokyo"));
        try(MockedStatic<ZonedDateTime> zonedDateTimeMockedStatic = Mockito.mockStatic(ZonedDateTime.class)){
            zonedDateTimeMockedStatic.when(ZonedDateTime::now).thenReturn(zonedDateTime);

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            UserUpdateForm userUpdateForm = new UserUpdateForm(2,"港一3", "河合", "k_kawai3",
                    true, "AAAAAAAA", "P@ssword1!", "def@example.com");
            String requestBody = ow.writeValueAsString(userUpdateForm);
            String response = mockMvc.perform(patch("/user_setting")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isNotFound())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
            JSONAssert.assertEquals("""
               {
                  "timestamp": "2023-07-15T20:00+09:00[Asia/Tokyo]",
                  "message": "resource not found",
                  "status": "404",
                  "path": "/user_setting",
                  "error": "Not Found"
               }
                """, response, JSONCompareMode.STRICT);
        }
    }
}
