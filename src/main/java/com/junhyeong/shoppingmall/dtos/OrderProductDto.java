package com.junhyeong.shoppingmall.dtos;

public class OrderProductDto {
    private Long productId;
    private String productName;
    private Long productPrice;
    private Long optionId;
    private String productOption;
    private Long productQuantity;
    private String productImage;
    private boolean writable;

    public OrderProductDto(Long productId, String productName, Long productPrice,
                           Long optionId, String productOption, Long productQuantity, String productImage, boolean writable) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.optionId = optionId;
        this.productOption = productOption;
        this.productQuantity = productQuantity;
        this.productImage = productImage;
        this.writable = writable;
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

    public boolean isWritable() {
        return writable;
    }
}
