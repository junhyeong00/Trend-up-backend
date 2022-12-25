package com.junhyeong.shoppingmall.dtos;

import java.util.List;

public class ReviewsDto {
    private List<ReviewDto> reviews;
    private int totalPageCount;
    private Long totalReviewCount;
    private Double totalRating;

    public ReviewsDto(List<ReviewDto> reviews, int totalPageCount, Long totalReviewCount, double totalRating) {
        this.reviews = reviews;
        this.totalPageCount = totalPageCount;
        this.totalReviewCount = totalReviewCount;
        this.totalRating = Double.valueOf(String.format("%.1f",totalRating));
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public Long getTotalReviewCount() {
        return totalReviewCount;
    }

    public Double getTotalRating() {
        return totalRating;
    }
}
