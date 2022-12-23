package com.junhyeong.shoppingmall.exceptions;

public class UserNotFound extends RuntimeException {
    public UserNotFound() {
        super("존재하지 않는 아이디입니다");
    }
}
