package com.junhyeong.shoppingmall.dtos;

public class UpdateCartDto {
    private String items;

    public UpdateCartDto() {
    }

    public UpdateCartDto(String items) {
        this.items = items;
    }

    public String getItems() {
        return items;
    }
}
