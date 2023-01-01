package com.junhyeong.shoppingmall.dtos;

public class CartDto {
    private String items;

    public CartDto(String items) {
        this.items = items;
    }

    public String getItems() {
        return items;
    }
}
