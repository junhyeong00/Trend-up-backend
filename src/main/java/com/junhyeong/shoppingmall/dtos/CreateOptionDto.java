package com.junhyeong.shoppingmall.dtos;

public class CreateOptionDto {
    private String optionName;
    private Long optionPrice;

    public CreateOptionDto() {
    }

    public CreateOptionDto(String optionName, Long optionPrice) {
        this.optionName = optionName;
        this.optionPrice = optionPrice;
    }

    public String getOptionName() {
        return optionName;
    }

    public Long getOptionPrice() {
        return optionPrice;
    }
}
