package com.junhyeong.shoppingmall.dtos;

public class OrderResultDto {
    private Long orderId;
    private String receiver;
    private String phoneNumber;
    private Long zipCode;
    private String roadAddress;
    private String detailAddress;
    private Long payment;
    private Long totalPrice;
    private Long deliveryFee;

    public OrderResultDto(Long orderId, String receiver, String phoneNumber,
                          Long zipCode, String roadAddress, String detailAddress,
                          Long payment, Long totalPrice, Long deliveryFee) {
        this.orderId = orderId;
        this.receiver = receiver;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.payment = payment;
        this.totalPrice = totalPrice;
        this.deliveryFee = deliveryFee;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Long getZipCode() {
        return zipCode;
    }

    public String getRoadAddress() {
        return roadAddress;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public Long getPayment() {
        return payment;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public Long getDeliveryFee() {
        return deliveryFee;
    }
}
