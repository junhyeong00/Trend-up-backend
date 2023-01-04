package com.junhyeong.shoppingmall.dtos;

import javax.validation.constraints.NotBlank;

public class InquiryRequestDto {
    private Long productId;

    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    private Boolean isSecret;

    public InquiryRequestDto(Long productId, String title, String content, Boolean isSecret) {
        this.productId = productId;
        this.title = title;
        this.content = content;
        this.isSecret = isSecret;
    }

    public Long getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Boolean getIsSecret() {
        return isSecret;
    }
}
