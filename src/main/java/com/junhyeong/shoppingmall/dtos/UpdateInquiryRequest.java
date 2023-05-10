package com.junhyeong.shoppingmall.dtos;

import com.junhyeong.shoppingmall.models.inquiry.Content;
import com.junhyeong.shoppingmall.models.inquiry.Title;

public class UpdateInquiryRequest {
    private Long inquiryId;
    private Title title;
    private Content content;
    private Boolean isSecret;

    public UpdateInquiryRequest(Long inquiryId, Title title, Content content, Boolean isSecret) {
        this.inquiryId = inquiryId;
        this.title = title;
        this.content = content;
        this.isSecret = isSecret;
    }

    public static UpdateInquiryRequest of(UpdateInquiryDto updateInquiryDto, Long inquiryId) {
        return new UpdateInquiryRequest(
                inquiryId,
                new Title(updateInquiryDto.getTitle()),
                new Content(updateInquiryDto.getContent()),
                updateInquiryDto.getIsSecret()
        );
    }

    public static UpdateInquiryRequest fake(Long inquiryId) {
        return new UpdateInquiryRequest(inquiryId,
                new Title("재입고 질문"), new Content("재입고 언제 될까요?"), false);
    }

    public Long getInquiryId() {
        return inquiryId;
    }

    public Title getTitle() {
        return title;
    }

    public Content getContent() {
        return content;
    }

    public Boolean getSecret() {
        return isSecret;
    }
}
