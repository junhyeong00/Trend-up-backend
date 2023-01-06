package com.junhyeong.shoppingmall.exceptions;

public class isNotWriter extends RuntimeException {
    public isNotWriter() {
        super("수정 및 삭제는 자신의 글만 가능합니다");
    }
}
