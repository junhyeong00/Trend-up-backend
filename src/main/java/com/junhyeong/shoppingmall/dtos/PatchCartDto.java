package com.junhyeong.shoppingmall.dtos;

public class PatchCartDto {
    private String items;

    public PatchCartDto() {
    }

    public PatchCartDto(String items) {
        this.items = items;
    }

    public String getItems() {
        return items;
    }
}
