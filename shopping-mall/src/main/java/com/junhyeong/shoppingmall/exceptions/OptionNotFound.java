package com.junhyeong.shoppingmall.exceptions;

public class OptionNotFound extends RuntimeException{
    public OptionNotFound() {
        super("없는 옵션입니다");
    }
}
