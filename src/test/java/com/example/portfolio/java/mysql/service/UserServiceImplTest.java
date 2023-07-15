package com.example.portfolio.java.mysql.service;

import com.example.portfolio.java.mysql.controller.LoginResponse;
import com.example.portfolio.java.mysql.entity.User;
import com.example.portfolio.java.mysql.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

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
}
