package com.junhyeong.shoppingmall.dtos;

public class AnswerResultDto {
    private Long AnswerId;

    public AnswerResultDto(Long answerId) {
        AnswerId = answerId;
    }

    public Long getAnswerId() {
        return AnswerId;
    }
}
