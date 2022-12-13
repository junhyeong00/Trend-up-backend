package com.junhyeong.shoppingmall.models;

import javax.persistence.Embeddable;

@Embeddable
public class OrderProduct {
    private Long productId;

    private String productName;

    private Long productPrice;

    private String productOption;

    private Long productQuantity;

    private String productImage;

    public OrderProduct() {
    }

    public OrderProduct(Long productId, String productName, Long productPrice,
                        String productOption, Long productQuantity, String productImage) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productOption = productOption;
        this.productQuantity = productQuantity;
        this.productImage = productImage;
    }

    public Long productId() {
        return productId;
    }

    public String productName() {
        return productName;
    }

    public Long productPrice() {
        return productPrice;
    }

    public Long productQuantity() {
        return productQuantity;
    }

    public String productOption() {
        return productOption;
    }

    public String productImage() {
        return productImage;
    }
}
