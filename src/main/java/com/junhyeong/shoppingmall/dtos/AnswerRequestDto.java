package com.junhyeong.shoppingmall.dtos;

import javax.validation.constraints.NotBlank;

public class AnswerRequestDto {
    private Long inquiryId;

    @NotBlank(message = "답변 내용을 입력해주세요")
    private String comment;

    public AnswerRequestDto(Long inquiryId, String comment) {
        this.inquiryId = inquiryId;
        this.comment = comment;
    }

    public Long getInquiryId() {
        return inquiryId;
    }

    public String getComment() {
        return comment;
    }
}
