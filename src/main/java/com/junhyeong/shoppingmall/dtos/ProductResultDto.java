package com.junhyeong.shoppingmall.dtos;

public class ProductResultDto {
    private Long productId;

    public ProductResultDto(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
