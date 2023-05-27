package com.junhyeong.shoppingmall.dtos;

import com.junhyeong.shoppingmall.models.order.Address;
import com.junhyeong.shoppingmall.models.order.Order;
import com.junhyeong.shoppingmall.models.order.OrderProduct;
import com.junhyeong.shoppingmall.models.order.PhoneNumber;

import java.util.ArrayList;
import java.util.List;

public class OrderRequest {
    private PhoneNumber phoneNumber;
    private String receiver;
    private Long payment;
    private Long totalPrice;
    private Long deliveryFee;
    private List<CreateOrderProductDto> orderProductDtos;
    private String deliveryRequest;
    private Address address;

    public OrderRequest(PhoneNumber phoneNumber, String receiver, Long payment,
                        Long totalPrice, Long deliveryFee, List<CreateOrderProductDto> orderProductDtos,
                        String deliveryRequest, Address address) {
        this.phoneNumber = phoneNumber;
        this.receiver = receiver;
        this.payment = payment;
        this.totalPrice = totalPrice;
        this.deliveryFee = deliveryFee;
        this.orderProductDtos = orderProductDtos;
        this.deliveryRequest = deliveryRequest;
        this.address = address;
    }

    public static OrderRequest fake() {
        Address address = new Address(123L, "인천", "102호");
        List<CreateOrderProductDto> orderProducts = List.of(new CreateOrderProductDto(1L, 1L, 1L));
        PhoneNumber phoneNumber = new PhoneNumber("01012341234");

        return new OrderRequest(phoneNumber, "배준형", 23000L, 20000L, 3000L,
                orderProducts, "안전하게 배송해주세요", address);
    }

    public static OrderRequest of(OrderRequestDto orderRequestDto) {
        return new OrderRequest(
                new PhoneNumber(orderRequestDto.getPhoneNumber()),
                orderRequestDto.getReceiver(),
                orderRequestDto.getPayment(),
                orderRequestDto.getTotalPrice(),
                orderRequestDto.getDeliveryFee(),
                orderRequestDto.getOrderProducts(),
                orderRequestDto.getDeliveryRequest(),
                new Address(
                        orderRequestDto.getZipCode(),
                        orderRequestDto.getRoadAddress(),
                        orderRequestDto.getDetailAddress())
        );
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public String getReceiver() {
        return receiver;
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

    public List<CreateOrderProductDto> getOrderProducts() {
        return orderProductDtos;
    }

    public String getDeliveryRequest() {
        return deliveryRequest;
    }

    public Address getAddress() {
        return address;
    }
}
