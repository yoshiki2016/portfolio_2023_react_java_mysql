package com.example.portfolio.java.mysql.mapper;

import com.example.portfolio.java.mysql.entity.User;
import com.github.database.rider.core.api.dataset.DataSet;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserMapperTest {
    @Autowired
    UserMapper userMapper;

    // テストケースの前処理、外部キー制約を無効にする。
    @BeforeAll
    public static void initializeDb(@Autowired DataSource dataSource){
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("user/foreignKeyChecksFalse.sql"));
        populator.execute(dataSource);
    }

    // テストケースの後処理、外部キー制約を有効にする。
    @AfterAll
    public static void finalizeDb(@Autowired DataSource dataSource){
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("user/foreignKeyChecksTrue.sql"));
        populator.execute(dataSource);
    }

    @Test
    @DataSet(value = "user/userList.yml")
    @Transactional
    public void userテーブルに存在するusernameとpasswordのペアが存在する時Userを返却すること () {
        Optional<User> user = userMapper.login("k_kawai", "W-DSVjma");
        // assertThat を利用できるようにする。
        assertThat(user).contains(
                new User(2, "港一", "河合", "k_kawai", "W-DSVjma", "kouichikawai@ifkmjxhuh.kt")
        );
    }
}
