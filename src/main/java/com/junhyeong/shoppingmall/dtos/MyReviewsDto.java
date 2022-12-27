package com.junhyeong.shoppingmall.dtos;

import java.util.List;

public class MyReviewsDto {
    private List<ReviewDto> reviews;
    private int totalPageCount;

    public MyReviewsDto(List<ReviewDto> reviews, int totalPageCount) {
        this.reviews = reviews;
        this.totalPageCount = totalPageCount;
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }
}
