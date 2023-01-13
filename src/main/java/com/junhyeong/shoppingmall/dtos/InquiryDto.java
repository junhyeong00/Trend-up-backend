package com.junhyeong.shoppingmall.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class InquiryDto {
    private Long id;
    private String userName;
    private String productName;
    private String title;
    private String content;
    private Boolean answerStatus;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createAt;
    private Boolean isSecret;
    private Boolean isMine;
    private String comment;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime answerCreateAt;

    public InquiryDto(Long id, String userName, String title, String content, boolean answerStatus,
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
                      boolean answerStatus, LocalDateTime createAt, Boolean isSecret) {
        this.id = id;
        this.userName = userName;
        this.productName = productName;
        this.title = title;
        this.content = content;
        this.answerStatus = answerStatus;
        this.createAt = createAt;
        this.isSecret = isSecret;
    }

    public InquiryDto(Long id, String userName, String title, String content, boolean answerStatus,
                      LocalDateTime createAt, Boolean isSecret, Boolean isMine, String comment,
                      LocalDateTime answerCreateAt) {
        this.id = id;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.answerStatus = answerStatus;
        this.createAt = createAt;
        this.isSecret = isSecret;
        this.isMine = isMine;
        this.comment = comment;
        this.answerCreateAt = answerCreateAt;
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public Boolean getIsSecret() {
        return isSecret;
    }

    public Boolean getIsMine() {
        return isMine;
    }

    public boolean isAnswerStatus() {
        return answerStatus;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getAnswerCreateAt() {
        return answerCreateAt;
    }
}
