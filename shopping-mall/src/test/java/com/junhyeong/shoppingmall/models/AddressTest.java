package com.junhyeong.shoppingmall.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    @Test
    void equals() {
        assertThat(new Address(123L, "인천", "102호"))
                .isEqualTo(new Address(123L, "인천", "102호"));

        assertThat(new Address(123L, "인천", "102호"))
                .isNotEqualTo(new Address(1234L, "서울", "102호"));

        assertThat(new Address(123L, "인천", "102호"))
                .isNotEqualTo(null);
    }
}
