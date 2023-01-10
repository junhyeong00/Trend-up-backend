package com.junhyeong.shoppingmall.exceptions;

public class CategoryNotFound extends RuntimeException {
    public CategoryNotFound() {
        super("존재하지 않는 카테고리입니다");
    }
}
