package com.junhyeong.shoppingmall.models;

import com.junhyeong.shoppingmall.models.vo.PhoneNumber;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PhoneNumberTest {
    @Test
    void testEquals() {
        assertThat(new PhoneNumber("01012341234"))
                .isEqualTo(new PhoneNumber("01012341234"));

        assertThat(new PhoneNumber("01012341234"))
                .isNotEqualTo(new PhoneNumber("01234"));

        assertThat(new PhoneNumber("01012341234"))
                .isNotEqualTo(null);

        assertThat(new PhoneNumber("01012341234"))
                .isNotEqualTo("01012341234");
    }
}
