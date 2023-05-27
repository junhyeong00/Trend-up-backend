package com.junhyeong.shoppingmall.exceptions;

public class IsNotWriter extends RuntimeException {
    public IsNotWriter() {
        super("수정 및 삭제는 자신의 글만 가능합니다");
    }
}
