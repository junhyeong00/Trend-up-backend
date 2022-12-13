package com.junhyeong.shoppingmall.models;

import com.junhyeong.shoppingmall.dtos.OrderResultDto;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    private String phoneNumber;

    private String receiver;

    private Long payment;

    private Long totalPrice;

    private Long deliveryFee;

    private String deliveryRequest;

    @ElementCollection
    @CollectionTable(name = "order_product")
    private List<OrderProduct> OrderProducts = new ArrayList<>();

    @Embedded
    private Address address;

    @CreationTimestamp
    private LocalDateTime createAt;

    public Order() {
    }

    public Order(Long userId, String phoneNumber,
                 String receiver, Long payment, Long totalPrice,
                 Long deliveryFee, String deliveryRequest, List<OrderProduct> OrderProducts,
                 Address address) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.receiver = receiver;
        this.payment = payment;
        this.totalPrice = totalPrice;
        this.deliveryFee = deliveryFee;
        this.deliveryRequest = deliveryRequest;
        this.OrderProducts = OrderProducts;
        this.address = address;
    }

    public Order(Long id, Long userId, String phoneNumber,
                 String receiver, Long payment, Long totalPrice,
                 Long deliveryFee, String deliveryRequest,
                 List<OrderProduct> orderProducts, Address address) {
        this.id = id;
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.receiver = receiver;
        this.payment = payment;
        this.totalPrice = totalPrice;
        this.deliveryFee = deliveryFee;
        this.deliveryRequest = deliveryRequest;
        OrderProducts = orderProducts;
        this.address = address;
    }

    public static Order fake(Long orderId) {
        Address address = new Address(123L, "인천", "102호");
        List<OrderProduct> orderProducts = new ArrayList<>();
        OrderProduct orderProduct = new OrderProduct(1L, "가디건", 10000L, "반짝반짝",1L, null);
        orderProducts.add(orderProduct);

        return new Order(orderId, 1L, "01012341234", "배준형", 20000L, 17000L, 3000L,
                "", orderProducts,  address);
    }

    public OrderResultDto toOrderResultDto() {
        return new OrderResultDto(id, receiver, phoneNumber,
                address.zipCode(), address.roadAddress(), address.detailAddress(),
                payment, totalPrice, deliveryFee);
    }

    public Long id() {
        return id;
    }

    public Long userId() {
        return userId;
    }

    public String phoneNumber() {
        return phoneNumber;
    }

    public String receiver() {
        return receiver;
    }

    public Long payment() {
        return payment;
    }

    public Long totalPrice() {
        return totalPrice;
    }

    public Long deliveryFee() {
        return deliveryFee;
    }

    public String deliveryRequest() {
        return deliveryRequest;
    }

    public List<OrderProduct> orderProducts() {
        return OrderProducts;
    }

    public Address address() {
        return address;
    }

    public LocalDateTime createAt() {
        return createAt;
    }
}
