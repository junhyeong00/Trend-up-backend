package com.junhyeong.shoppingmall.dtos;

import com.junhyeong.shoppingmall.models.inquiry.Content;
import com.junhyeong.shoppingmall.models.inquiry.Title;

public class CreateInquiryRequest {
    private Long productId;
    private Title title;
    private Content content;
    private Boolean isSecret;

    public CreateInquiryRequest(Long productId, Title title, Content content, Boolean isSecret) {
        this.productId = productId;
        this.title = title;
        this.content = content;
        this.isSecret = isSecret;
    }

    public static CreateInquiryRequest of(CreateInquiryRequestDto createInquiryRequestDto) {
        return new CreateInquiryRequest(
                createInquiryRequestDto.getProductId(),
                new Title(createInquiryRequestDto.getTitle()),
                new Content(createInquiryRequestDto.getContent()),
                createInquiryRequestDto.getIsSecret()
        );
    }

    public static CreateInquiryRequest fake(Long productId) {
        return new CreateInquiryRequest(productId,
                new Title("재입고 질문"), new Content("재입고 언제 될까요?"), false);
    }

    public Long getProductId() {
        return productId;
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
