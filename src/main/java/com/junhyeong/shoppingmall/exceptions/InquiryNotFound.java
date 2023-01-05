package com.junhyeong.shoppingmall.exceptions;

public class InquiryNotFound extends RuntimeException {
    public InquiryNotFound() {
        super("존재하지 않는 문의 글입니다");
    }
}
