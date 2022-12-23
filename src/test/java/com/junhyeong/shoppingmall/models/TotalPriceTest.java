package com.junhyeong.shoppingmall.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TotalPriceTest {
    @Test
    void testEquals() {
        assertThat(new TotalPrice(100L))
                .isEqualTo(new TotalPrice(100L));

        assertThat(new TotalPrice(100L))
                .isNotEqualTo(null);

        assertThat(new TotalPrice(100L))
                .isNotEqualTo(new TotalPrice(123L));

        assertThat(new TotalPrice(100L))
                .isNotEqualTo(100L);
    }
}
