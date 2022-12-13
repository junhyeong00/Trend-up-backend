package com.junhyeong.shoppingmall.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Option {
    @Id
    @GeneratedValue
    private Long id;

    private Long productId;

    private String name;

    private Long optionPrice;

    private Long stockQuantity;

    public Option() {
    }

    public Option(Long id, Long productId, String name, Long optionPrice, Long stockQuantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.optionPrice = optionPrice;
        this.stockQuantity = stockQuantity;
    }

    public static Option fake(Long optionId) {
        return new Option(optionId, 1L, "반짝반짝", 10000L, 2L);
    }

    public Long id() {
        return id;
    }

    public Long productId() {
        return productId;
    }

    public String name() {
        return name;
    }

    public Long optionPrice() {
        return optionPrice;
    }

    public Long stockQuantity() {
        return stockQuantity;
    }

    public void sell(Long orderQuantity) {
        this.stockQuantity -= orderQuantity;
    }
}
