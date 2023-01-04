package com.junhyeong.shoppingmall.dtos;

public class InquiryResultDto {
    private Long inquiryId;

    public InquiryResultDto(Long inquiryId) {
        this.inquiryId = inquiryId;
    }

    public Long getInquiryId() {
        return inquiryId;
    }
}
