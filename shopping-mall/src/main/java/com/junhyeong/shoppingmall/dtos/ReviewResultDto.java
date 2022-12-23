package com.junhyeong.shoppingmall.dtos;

public class ReviewResultDto {
    private Long reviewId;

    public ReviewResultDto(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getReviewId() {
        return reviewId;
    }
}
