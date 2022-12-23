package com.junhyeong.shoppingmall.exceptions;

public class AuthenticationError extends RuntimeException {
    public AuthenticationError() {
        super("Authentication error");
    }
}
