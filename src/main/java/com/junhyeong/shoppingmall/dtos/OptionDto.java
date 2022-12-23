package com.junhyeong.shoppingmall.dtos;

public class OptionDto {
    private Long id;
    private Long productId;
    private String name;
    private Long optionPrice;

    public OptionDto(Long id, Long productId, String name, Long optionPrice) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.optionPrice = optionPrice;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Long getOptionPrice() {
        return optionPrice;
    }
}
