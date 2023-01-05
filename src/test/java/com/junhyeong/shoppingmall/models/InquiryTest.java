package com.junhyeong.shoppingmall.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InquiryTest {
    @Test
    void checkMine() {
        Long inquiryId = 1L;
        Long userId = 1L;
        Inquiry inquiry = Inquiry.fake(inquiryId, userId);

        Long otherUserId = 2L;
        assertThat(inquiry.checkMine(userId)).isTrue();
        assertThat(inquiry.checkMine(otherUserId)).isFalse();
    }
}
