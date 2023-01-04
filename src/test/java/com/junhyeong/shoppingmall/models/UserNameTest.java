package com.junhyeong.shoppingmall.models;

import com.junhyeong.shoppingmall.models.vo.UserName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserNameTest {
    @Test
    void equals() {
        assertThat(new UserName("test123"))
                .isEqualTo(new UserName("test123"));

        assertThat(new UserName("test123"))
                .isNotEqualTo(new UserName("test"));

        assertThat(new UserName("test123"))
                .isNotEqualTo(null);

        assertThat(new UserName("test123"))
                .isNotEqualTo("test123");
    }
}
