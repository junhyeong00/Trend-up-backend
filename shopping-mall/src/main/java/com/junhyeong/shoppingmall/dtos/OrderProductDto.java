package com.junhyeong.shoppingmall.dtos;

public class OrderProductDto {
    private Long productId;
    private String productName;
    private Long productPrice;
    private String productOption;
    private Long productQuantity;
    private String productImage;

    public OrderProductDto(Long productId, String productName, Long productPrice,
                           String productOption, Long productQuantity, String productImage) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productOption = productOption;
        this.productQuantity = productQuantity;
        this.productImage = productImage;
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

    public String getProductOption() {
        return productOption;
    }

    public Long getProductQuantity() {
        return productQuantity;
    }

    public String getProductImage() {
        return productImage;
    }
}
