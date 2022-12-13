package com.junhyeong.shoppingmall.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OptionTest {
    @Test
    void sell() {
        Option option = new Option(1L, 1L, "사이즈 L", 1L, 4L);

        assertThat(option.stockQuantity()).isEqualTo(4);

        option.sell(2L);

        assertThat(option.stockQuantity()).isEqualTo(2);
    }
}