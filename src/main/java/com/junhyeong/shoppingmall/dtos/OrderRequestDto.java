package com.junhyeong.shoppingmall.dtos;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class OrderRequestDto {
    @NotBlank(message = "받는 분 성함을 입력해주세요")
    private String receiver;

    @NotBlank(message = "받는 분 번호를 입력해주세요")
    private String phoneNumber;

    private Long payment;

    private Long totalPrice;

    private Long deliveryFee;

    private String deliveryRequest;

    private Long zipCode;

    @NotBlank(message = "주소를 입력해주세요")
    private String roadAddress;

    private String detailAddress;

    private List<CreateOrderProductDto> orderProducts;

    public OrderRequestDto() {
    }

    public OrderRequestDto(String receiver, String phoneNumber,
                           Long payment, Long totalPrice,
                           Long deliveryFee, String deliveryRequest, Long zipCode,
                           String roadAddress, String detailAddress,
                           List<CreateOrderProductDto> orderProducts) {
        this.receiver = receiver;
        this.phoneNumber = phoneNumber;
        this.payment = payment;
        this.totalPrice = totalPrice;
        this.deliveryFee = deliveryFee;
        this.deliveryRequest = deliveryRequest;
        this.zipCode = zipCode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.orderProducts = orderProducts;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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

    public String getDeliveryRequest() {
        return deliveryRequest;
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

    public List<CreateOrderProductDto> getOrderProducts() {
        return orderProducts;
    }
}
