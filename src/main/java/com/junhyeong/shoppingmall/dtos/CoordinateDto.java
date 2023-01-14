package com.junhyeong.shoppingmall.dtos;

public class CoordinateDto {
    private String x;
    private Long y;

    public CoordinateDto(String x, Long y) {
        this.x = x;
        this.y = y;
    }

    public String getX() {
        return x;
    }

    public Long getY() {
        return y;
    }
}
