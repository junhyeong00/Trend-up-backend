package com.junhyeong.shoppingmall.exceptions;

public class LoginFailed extends RuntimeException {
    public LoginFailed(String errorMessage) {
        super(errorMessage);
    }
}
