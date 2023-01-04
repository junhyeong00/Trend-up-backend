package com.junhyeong.shoppingmall.models;

import com.junhyeong.shoppingmall.models.vo.UserName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    @Test
    void authenticate() {
        User user = User.fake(new UserName("test123"));

        PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();

        user.changePassword("Password1234!", passwordEncoder);

        assertThat(user.authenticate("Password1234!", passwordEncoder)).isTrue();
        assertThat(user.authenticate("xxx", passwordEncoder)).isFalse();
    }
}
