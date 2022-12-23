package com.junhyeong.shoppingmall.enums;

public enum DeliveryStatus {
    SHIPPED("배송 준비중"),
    IN_TRANSIT("배송중"),
    DELIVERED("배송완료");

    private final String value;

    private DeliveryStatus(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
