package com.junhyeong.shoppingmall.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class InquiryDto {
    private Long id;
    private String userName;
    private String productName;
    private String title;
    private String content;
    private String answerStatus;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createAt;
    private Boolean isSecret;
    private Boolean isMine;

    public InquiryDto(Long id, String userName, String title, String content, String answerStatus,
                      LocalDateTime createAt, Boolean isSecret, Boolean isMine) {
        this.id = id;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.answerStatus = answerStatus;
        this.createAt = createAt;
        this.isSecret = isSecret;
        this.isMine = isMine;
    }

    public InquiryDto(Long id, String userName, String productName, String title, String content,
                      String answerStatus, LocalDateTime createAt, Boolean isSecret) {
        this.id = id;
        this.userName = userName;
        this.productName = productName;
        this.title = title;
        this.content = content;
        this.answerStatus = answerStatus;
        this.createAt = createAt;
        this.isSecret = isSecret;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getProductName() {
        return productName;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAnswerStatus() {
        return answerStatus;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public Boolean getIsSecret() {
        return isSecret;
    }

    public Boolean getIsMine() {
        return isMine;
    }
}
