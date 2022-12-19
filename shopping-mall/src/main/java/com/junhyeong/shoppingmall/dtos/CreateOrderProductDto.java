package com.junhyeong.shoppingmall.dtos;

public class CreateOrderProductDto {
    private Long productId;
    private Long optionId;
    private Long quantity;

    public CreateOrderProductDto(Long productId, Long optionId, Long quantity) {
        this.productId = productId;
        this.optionId = optionId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getOptionId() {
        return optionId;
    }

    public Long getQuantity() {
        return quantity;
    }
}
