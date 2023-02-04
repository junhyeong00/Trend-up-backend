package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.exceptions.RegisterFailed;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateUserServiceTest {
    CreateUserService createUserService;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);

        passwordEncoder = new Argon2PasswordEncoder();

        createUserService = new CreateUserService(userRepository, passwordEncoder);
    }

    @Test
    void register() {
        UserName userName = new UserName("test123");

        User user = User.fake(userName);

        String password = "password";

        user.changePassword(password, passwordEncoder);

        given(userRepository.save(any()))
                .willReturn(user);

        User createdUser = createUserService.register(user.name(), userName, password, password, "01012341234");

        assertThat(createdUser).isNotNull();

        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerWithAlreadyExistingUserName() {
        UserName userName = new UserName("test123");

        User user = User.fake(userName);

        String password = "password";

        user.changePassword(password, passwordEncoder);

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(user));

        assertThrows(RegisterFailed.class, () -> {
            createUserService.register(user.name(), userName, password, password, "01012341234");
        });

        verify(userRepository).findByUserName(userName);
    }

    @Test
    void registerWithNotMatchingPasswords() {
        UserName userName = new UserName("test123");

        User user = User.fake(userName);

        String password = "password";

        user.changePassword(password, passwordEncoder);

        String notMatchingPassword = "123123";
        assertThrows(RegisterFailed.class, () -> {
            createUserService.register(
                    user.name(), userName, password, notMatchingPassword, "01012341234");
        });
    }
}
