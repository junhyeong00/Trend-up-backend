package com.junhyeong.shoppingmall.exceptions;

public class AnswerWriteFailed extends RuntimeException {
    public AnswerWriteFailed() {
        super("이미 해당 문의에 대한 답변을 작성하였습니다");
    }
}
