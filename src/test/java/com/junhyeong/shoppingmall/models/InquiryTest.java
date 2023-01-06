package com.junhyeong.shoppingmall.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InquiryTest {
    @Test
    void isWriter() {
        Long inquiryId = 1L;
        Long userId = 1L;
        Inquiry inquiry = Inquiry.fake(inquiryId, userId);

        Long otherUserId = 2L;
        assertThat(inquiry.isWriter(userId)).isTrue();
        assertThat(inquiry.isWriter(otherUserId)).isFalse();
    }

    @Test
    void update() {
        Long inquiryId = 1L;
        Long userId = 1L;
        Inquiry inquiry = Inquiry.fake(inquiryId, userId);

        assertThat(inquiry.title()).isEqualTo("재입고 질문");

        inquiry.update("색상 문의", "빨간색은 없나요?" , false);

        assertThat(inquiry.title()).isEqualTo("색상 문의");
    }
}
