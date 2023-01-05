package com.junhyeong.shoppingmall.exceptions;

public class DeleteInquiryFailed extends RuntimeException {
    public DeleteInquiryFailed() {
        super("자신의 글만 삭제할 수 있습니다");
    }
}
