package com.junhyeong.shoppingmall.dtos;

public class DeleteReviewDto {
    private Long reviewId;

    public DeleteReviewDto(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getReviewId() {
        return reviewId;
    }
}
