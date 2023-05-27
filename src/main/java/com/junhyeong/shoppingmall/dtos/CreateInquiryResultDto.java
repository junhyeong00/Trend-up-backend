package com.junhyeong.shoppingmall.dtos;

public class CreateInquiryResultDto {
    private Long inquiryId;

    public CreateInquiryResultDto(Long inquiryId) {
        this.inquiryId = inquiryId;
    }

    public Long getInquiryId() {
        return inquiryId;
    }
}
