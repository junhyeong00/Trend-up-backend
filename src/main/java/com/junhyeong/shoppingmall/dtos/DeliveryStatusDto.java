package com.junhyeong.shoppingmall.dtos;

public class DeliveryStatusDto {
    private String deliveryStatus;

    public DeliveryStatusDto() {
    }

    public DeliveryStatusDto(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }
}
