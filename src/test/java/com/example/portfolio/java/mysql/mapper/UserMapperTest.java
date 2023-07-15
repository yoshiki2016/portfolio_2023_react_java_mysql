package com.example.portfolio.java.mysql.mapper;

import com.example.portfolio.java.mysql.entity.User;
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
    public void userテーブルに存在するuserNameとpasswordのペアが存在する時Userを返却すること() {
        Optional<User> user = userMapper.login("k_kawai", "W-DSVjma");
        assertThat(user).contains(
                new User(2, "港一", "河合", "k_kawai", "W-DSVjma", "kouichikawai@ifkmjxhuh.kt")
        );
    }

    @Test
    @DataSet(value = "user/userList.yml")
    @Transactional
    public void userテーブルに存在しないuserNameとpasswordのペアが存在する時Optionalを返却すること() {
        Optional<User> user = userMapper.login("y_asaka", "Sr0LVDwX");
        assertThat(user).isEmpty();
    }

    @Test
    @DataSet(value = "user/userList.yml")
    @ExpectedDataSet(value = "user/userCreateList.yml", ignoreCols = "id")
    @Transactional
    public void DBにユーザーが追加された際にidがオートインクリメントされること() {
        User user4 = new User("秀加", "山内", "y_hideka", "nm_8BrTv", "hideka28544@epjtdsmc.vyt.edk");
        assertThat(user4.getId()).isEqualTo(0);
        userMapper.userRegister(user4);
        assertThat(user4.getId()).isGreaterThan(0);

        User user5 = new User("涼", "杉田", "s_ryo", "FLemJxow", "uqcztuhfqxryou86581@jgyecl.uck");
        assertThat(user5.getId()).isEqualTo(0);
        userMapper.userRegister(user5);
        assertThat(user5.getId()).isGreaterThan(0);

        // idがオートインクリメントされていることを確認する。
        // 後で作ったmovie5のidの方が大きいことを確認する。
        assertThat(user5.getId()).isGreaterThan(user4.getId());
    }

    @Test
    @DataSet(value = "user/userList.yml")
    @ExpectedDataSet(value = "user/userUpdateList.yml")
    @Transactional
    public void user情報を更新出来ること(){
        // パスワードを除いて更新出来ること
        userMapper.updateUserWithoutPassword(1, "琴美2", "田中", "t_kotomi2", "abc@example.com");

        // パスワードを含んで更新が出来ること
        userMapper.updateUserWithPassword(2, "港一3", "河合", "k_kawai3", "P@ssword1!", "def@example.com");
    }
}
