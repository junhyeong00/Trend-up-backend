package com.junhyeong.shoppingmall.dtos;

import javax.validation.constraints.NotBlank;

public class UpdateInquiryDto {
    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    private Boolean isSecret;

    public UpdateInquiryDto(String title, String content, Boolean isSecret) {
        this.title = title;
        this.content = content;
        this.isSecret = isSecret;
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
