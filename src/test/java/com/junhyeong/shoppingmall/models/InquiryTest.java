package com.junhyeong.shoppingmall.models;

import com.junhyeong.shoppingmall.dtos.InquiryDto;
import com.junhyeong.shoppingmall.exceptions.IsNotWriter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void checkWriterAuthority() {
        Long inquiryId = 1L;
        Long userId = 1L;
        Inquiry inquiry = Inquiry.fake(inquiryId, userId);

        Long otherUserId = 2L;

        assertDoesNotThrow(() -> inquiry.checkWriterAuthority(userId));
        assertThrows(IsNotWriter.class,
                () -> inquiry.checkWriterAuthority(otherUserId));
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


    @Test
    void changeToSecret() {
        Long inquiryId = 1L;
        Long userId = 1L;
        Inquiry inquiry = Inquiry.fake(inquiryId, userId);

        InquiryDto inquiryDto = inquiry.changeToSecret(true, false);

        assertThat(inquiryDto.getTitle()).isEqualTo("비밀글입니다");
        assertThat(inquiryDto.getContent()).isEqualTo("접근 권한이 없습니다");
    }
}
