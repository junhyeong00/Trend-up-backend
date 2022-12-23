package com.junhyeong.shoppingmall.utils;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.junhyeong.shoppingmall.models.UserName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {
    private JwtUtil jwtUtil;
    private final String secret = "SECRET";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(secret);
    }

    @Test
    void encodeAndDecode() {
        UserName userName = new UserName("test123");

        String token = jwtUtil.encode(userName);

        assertThat(token).contains(".");

        assertThat(jwtUtil.decode(token)).isEqualTo(userName);
    }

    @Test
    void decodeError() {
        assertThrows(JWTDecodeException.class, () -> {
            jwtUtil.decode("xxx");
        });
    }
}
