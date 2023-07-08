package com.example.portfolio.java.mysql.mapper;

import com.example.portfolio.java.mysql.entity.User;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserMapperTest {
    @Autowired
    UserMapper userMapper;

    @Test
    @DataSet(value = "./user/userList.yml")
    @Transactional
    public void userテーブルに存在するusernameとpasswordのペアが存在する時Userを返却すること () {
        Optional<User> user = userMapper.login("k_kawai", "W-DSVjma");
        // assertThat を利用できるようにする。
    }
}
