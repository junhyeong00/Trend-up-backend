package com.junhyeong.shoppingmall.exceptions;

public class UserNotFound extends RuntimeException {
    public UserNotFound() {
        super("없는 아이디입니다");
    }
}
