package com.junhyeong.shoppingmall.dtos;

public class ProductDto {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private String name;
    private Long price;
    private String image;
    private double totalRating;
    private Long totalReviewCount;

    public ProductDto() {
    }

    public ProductDto(Long id, Long category, String categoryName, String name, Long price,
                      String image, double totalRating, Long totalReviewCount) {
        this.id = id;
        this.categoryId = category;
        this.categoryName = categoryName;
        this.name = name;
        this.price = price;
        this.image = image;
        this.totalRating = Double.valueOf(String.format("%.1f", totalRating));
        this.totalReviewCount = totalReviewCount;
    }

    public Long getId() {
        return id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public double getTotalRating() {
        return totalRating;
    }

    public Long getTotalReviewCount() {
        return totalReviewCount;
    }
}
