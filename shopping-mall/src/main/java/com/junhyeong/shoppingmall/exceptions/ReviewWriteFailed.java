package com.junhyeong.shoppingmall.exceptions;

public class ReviewWriteFailed extends RuntimeException {
    public ReviewWriteFailed(String errorMessage) {
        super(errorMessage);
    }
}
