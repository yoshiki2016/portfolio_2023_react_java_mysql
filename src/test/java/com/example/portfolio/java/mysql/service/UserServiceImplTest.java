package com.example.portfolio.java.mysql.service;

import com.example.portfolio.java.mysql.controller.LoginResponse;
import com.example.portfolio.java.mysql.entity.User;
import com.example.portfolio.java.mysql.exception.ResourceNotFoundException;
import com.example.portfolio.java.mysql.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Mock
    UserMapper userMapper;

    @Test
    public void 指定のユーザーとパスワードがusersテーブルに存在する時userIdに数値とloginFlagにtrueが入ってること() {
        Mockito.doReturn(Optional.of(new User(1, "里歌", "浅川", "r_asakawa", "Sr0LVDwX", "nscjmxwh-hhtjrika773@lkzrsqkdxm.apl.iw")))
                .when(userMapper).login("r_asakawa", "Sr0LVDwX");
        LoginResponse actual = userServiceImpl.login("r_asakawa", "Sr0LVDwX");
        assertThat(actual).isEqualTo(new LoginResponse(1, true));
    }

    @Test
    public void 指定のユーザーとパスワードがusersテーブルに存在しない時userIdに0とloginFlagにfalseが入ってること() {
        Mockito.doReturn(Optional.empty()).when(userMapper).login("r_asakawa", "Sr0LVDwX");
        LoginResponse actual = userServiceImpl.login("r_asakawa", "Sr0LVDwX");
        assertThat(actual).isEqualTo(new LoginResponse(0, false));
    }

    @Test
    public void ユーザー登録できること() {
        User testUser = new User("山内", "秀加", "y_hideka",
                "nm_8BrTv", "hideka28544@epjtdsmc.vyt.edk");
        userServiceImpl.userRegister("山内", "秀加", "y_hideka",
                "nm_8BrTv", "hideka28544@epjtdsmc.vyt.edk");
        Mockito.verify(userMapper).userRegister(testUser);
    }

    @Test
    public void パスワードを除いてユーザー更新出来ること(){
        userServiceImpl.updateUser(1,"琴美2", "田中", "t_kotomi2",
                false, "", "", "abc@example.com");
        Mockito.verify(userMapper).updateUserWithoutPassword(1,"琴美2", "田中",
                "t_kotomi2", "abc@example.com");
    }

    @Test
    public void ユーザーIDとパスワードを確認し合致したらパスワードを含んでユーザー更新出来ること(){
        User oldUser = new User(2,"港一", "河合", "k_kawai",
                "Abc12345", "abc@example.com");
        Mockito.doReturn(Optional.of(oldUser))
                .when(userMapper).searchUser(2, "Abc12345");

        userServiceImpl.updateUser(2,"港一3", "河合", "k_kawai3",
                true, "Abc12345", "P@ssword1!", "def@example.com");
        Mockito.verify(userMapper).updateUserWithPassword(2,"港一3", "河合",
                "k_kawai3", "P@ssword1!", "def@example.com");
    }

    @Test
    public void ユーザーIDとパスワードを確認し合致しなかったら例外をthrowすること(){
        Mockito.doReturn(Optional.empty()).when(userMapper).searchUser(2, "Abc12345");
        assertThatThrownBy(() -> userServiceImpl.updateUser(2,"港一3", "河合", "k_kawai3",
                true, "Abc12345", "P@ssword1!", "def@example.com"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("resource not found");
    }
}
