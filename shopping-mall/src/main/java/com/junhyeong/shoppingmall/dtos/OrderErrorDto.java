package com.junhyeong.shoppingmall.dtos;

public class OrderErrorDto {
    private String message;

    public OrderErrorDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
