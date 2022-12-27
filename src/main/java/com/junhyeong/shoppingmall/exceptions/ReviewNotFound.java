package com.junhyeong.shoppingmall.exceptions;

public class ReviewNotFound extends RuntimeException {
    public ReviewNotFound() {
        super("존재하지 않는 리뷰입니다");
    }
}
