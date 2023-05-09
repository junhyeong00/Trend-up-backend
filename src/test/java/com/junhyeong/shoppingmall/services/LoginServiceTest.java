package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.exceptions.LoginFailed;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import com.junhyeong.shoppingmall.services.login.LoginService;
import com.junhyeong.shoppingmall.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LoginServiceTest {
    private LoginService loginService;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = new Argon2PasswordEncoder();
        loginService = new LoginService(userRepository, passwordEncoder, jwtUtil);
    }

    @Test
    void loginSuccess() {
        UserName userName = new UserName("test123");
        String password = "Password1234!";

        User user = User.fake(userName);
        user.changePassword(password, passwordEncoder);

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(user));

        User found = loginService.login(userName, password);

        assertThat(found.userName()).isEqualTo(userName);
    }

    @Test
    void loginFailWithIncorrectUserName() {
        assertThrows(LoginFailed.class, () -> {
            loginService.login(new UserName("xxx"), "password1234!");
        });
    }

    @Test
    void loginFailWithIncorrectPassword() {
        assertThrows(LoginFailed.class, () -> {
            loginService.login(new UserName("test123"), "xxx");
        });
    }
}
