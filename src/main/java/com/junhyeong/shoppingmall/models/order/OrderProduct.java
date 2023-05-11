package com.junhyeong.shoppingmall.models.order;

import com.junhyeong.shoppingmall.dtos.OrderProductDto;
import com.junhyeong.shoppingmall.models.option.Option;
import com.junhyeong.shoppingmall.models.product.Product;

import javax.persistence.Embeddable;

@Embeddable
public class OrderProduct {
    private Long productId;

    private String productName;

    private Long productPrice;

    private Long optionId;

    private String productOption;

    private Long productQuantity;

    private String productImage;

    public OrderProduct() {
    }

    public OrderProduct(Long productId, String productName,
                        Long productPrice, Long optionId,
                        String productOption, Long productQuantity,
                        String productImage) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.optionId = optionId;
        this.productOption = productOption;
        this.productQuantity = productQuantity;
        this.productImage = productImage;
    }

    public static OrderProduct fake(Long productId, Long optionId) {
        Product product = Product.fake(productId);
        Option option = Option.fake(optionId);
        return new OrderProduct(productId, product.name(), 10000L, optionId, option.name(), 1L, null);
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

    public Long optionId() {
        return optionId;
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

    public OrderProductDto toOrderProductDto(boolean writable) {
        return new OrderProductDto(productId, productName, productPrice, optionId,
                productOption, productQuantity, productImage, writable);
    }

    public OrderProductDto toOrderProductDto() {
        return new OrderProductDto(productId, productName, productPrice, optionId,
                productOption, productQuantity, productImage);
    }
}
