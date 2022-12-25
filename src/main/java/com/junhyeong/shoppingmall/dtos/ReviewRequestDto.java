package com.junhyeong.shoppingmall.dtos;

public class ReviewRequestDto {
    private Long orderId;

    private Long productId;

    private String productName;

    private Long productPrice;

    private Long optionId;

    private String productOption;

    private Long productQuantity;

    private String productImage;

    private Double rating;

    private String content;

    private String imageUrl;

    public ReviewRequestDto(Long orderId, Long productId, String productName,
                            Long productPrice, Long optionId, String productOption,
                            Long productQuantity, String productImage, Double rating,
                            String content, String imageUrl) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.optionId = optionId;
        this.productOption = productOption;
        this.productQuantity = productQuantity;
        this.productImage = productImage;
        this.rating = rating;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public Long getOptionId() {
        return optionId;
    }

    public String getProductOption() {
        return productOption;
    }

    public Long getProductQuantity() {
        return productQuantity;
    }

    public String getProductImage() {
        return productImage;
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
