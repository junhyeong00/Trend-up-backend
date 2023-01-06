package com.junhyeong.shoppingmall.dtos;

public class UpdateReviewDto {
    private Double rating;
    private String content;
    private String imageUrl;

    public UpdateReviewDto(Double rating, String content, String imageUrl) {
        this.rating = rating;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public Double getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
