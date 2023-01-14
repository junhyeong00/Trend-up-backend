package com.junhyeong.shoppingmall.dtos;

public class DeliveryInformationDto {
    private Long shippedCount;
    private Long inTransitCount;
    private Long deliveredCount;

    public DeliveryInformationDto(Long shippedCount, Long inTransitCount, Long deliveredCount) {
        this.shippedCount = shippedCount;
        this.inTransitCount = inTransitCount;
        this.deliveredCount = deliveredCount;
    }

    public Long getShippedCount() {
        return shippedCount;
    }

    public Long getInTransitCount() {
        return inTransitCount;
    }

    public Long getDeliveredCount() {
        return deliveredCount;
    }
}
