package com.junhyeong.shoppingmall.exceptions;

public class ProductNotFound extends RuntimeException{
    public ProductNotFound() {
        super("없는 상품입니다");
    }
}
