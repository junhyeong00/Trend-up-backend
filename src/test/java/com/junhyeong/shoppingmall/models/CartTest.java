package com.junhyeong.shoppingmall.models;

import com.junhyeong.shoppingmall.models.cart.Cart;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CartTest {
    @Test
    void equals() {
        assertThat(new Cart("items"))
                .isEqualTo(new Cart("items"));

        assertThat(new Cart("items"))
                .isNotEqualTo(new Cart(""));

        assertThat(new Cart("items"))
                .isNotEqualTo(null);

        assertThat(new Cart("items"))
                .isNotEqualTo("items");
    }
}
