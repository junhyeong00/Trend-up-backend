package com.junhyeong.shoppingmall.dtos;

public class LoginErrorDto {
    private final String errorMessage;

    public LoginErrorDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
