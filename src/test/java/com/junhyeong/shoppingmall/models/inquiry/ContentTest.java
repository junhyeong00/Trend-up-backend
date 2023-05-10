package com.junhyeong.shoppingmall.models.inquiry;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContentTest {
    @Test
    void equality() {
        Content content1 = new Content("물어보고 싶습니다");
        Content content2 = new Content("물어보고 싶습니다");
        Content content3 = new Content("이것을 물어보고 싶습니다");

        assertThat(content1).isEqualTo(content2);
        assertThat(content1).isNotEqualTo(content3);
    }
}
