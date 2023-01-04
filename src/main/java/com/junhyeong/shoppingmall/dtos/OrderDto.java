package com.junhyeong.shoppingmall.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.junhyeong.shoppingmall.models.vo.Address;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {
    private Long id;
    private String receiver;
    private String phoneNumber;
    private Long payment;
    private Long totalPrice;
    private Long deliveryFee;
    private String deliveryRequest;
    private List<OrderProductDto> orderProducts;
    private Long zipCode;
    private String roadAddress;
    private String detailAddress;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createAt;
    private String deliveryStatus;

    public OrderDto(Long id, String receiver, String phoneNumber,
                    Long payment, Long totalPrice, Long deliveryFee,
                    String deliveryRequest, List<OrderProductDto> orderProducts,
                    Address address, LocalDateTime createAt, String deliveryStatus) {
        this.id = id;
        this.receiver = receiver;
        this.phoneNumber = phoneNumber;
        this.payment = payment;
        this.totalPrice = totalPrice;
        this.deliveryFee = deliveryFee;
        this.deliveryRequest = deliveryRequest;
        this.orderProducts = orderProducts;
        this.zipCode = address.zipCode();
        this.roadAddress = address.roadAddress();
        this.detailAddress = address.detailAddress();
        this.createAt = createAt;
        this.deliveryStatus = deliveryStatus;
    }

    public Long getId() {
        return id;
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

    public List<OrderProductDto> getOrderProducts() {
        return orderProducts;
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }
}
