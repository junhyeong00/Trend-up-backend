package com.junhyeong.shoppingmall.dtos;

public class AnswerWriteErrorDto {
    private String message;

    public AnswerWriteErrorDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
