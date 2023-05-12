package com.junhyeong.shoppingmall.models.order;

import com.junhyeong.shoppingmall.dtos.OrderDto;
import com.junhyeong.shoppingmall.dtos.OrderProductDto;
import com.junhyeong.shoppingmall.dtos.OrderResultDto;
import com.junhyeong.shoppingmall.enums.DeliveryStatus;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_table")
public class Order {
    @Id
    private Long id;

    private Long userId;

    @Embedded
    private PhoneNumber phoneNumber;

    private String receiver;

    private Long payment;

    private Long totalPrice;

    private Long deliveryFee;

    private String deliveryRequest;

    private String deliveryStatus;

    @ElementCollection
    @CollectionTable(name = "order_product")
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Embedded
    private Address address;

    @CreationTimestamp
    private LocalDateTime createAt;

    public Order() {
    }

    public Order(Long id, Long userId, PhoneNumber phoneNumber,
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
        this.orderProducts = orderProducts;
        this.address = address;
        this.deliveryStatus = DeliveryStatus.SHIPPED.value();
    }

    public Order(Long id, Long userId, PhoneNumber phoneNumber,
                 String receiver, Long payment, Long totalPrice,
                 Long deliveryFee, String deliveryRequest,
                 String deliveryStatus, List<OrderProduct> orderProducts,
                 Address address, LocalDateTime createAt) {
        this.id = id;
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.receiver = receiver;
        this.payment = payment;
        this.totalPrice = totalPrice;
        this.deliveryFee = deliveryFee;
        this.deliveryRequest = deliveryRequest;
        this.deliveryStatus = deliveryStatus;
        this.orderProducts = orderProducts;
        this.address = address;
        this.createAt = createAt;
    }

    public static Order fake(Long orderId) {
        Address address = new Address(123L, "인천", "102호");
        List<OrderProduct> orderProducts = new ArrayList<>();
        OrderProduct orderProduct = OrderProduct.fake(1L, 1L);
        PhoneNumber phoneNumber = new PhoneNumber("01012341234");
        orderProducts.add(orderProduct);

        return new Order(orderId, 1L, phoneNumber, "배준형", 23000L, 20000L, 3000L,
                "", orderProducts,  address);
    }

    public OrderResultDto toOrderResultDto() {
        return new OrderResultDto(id, receiver, phoneNumber.value(),
                address.zipCode(), address.roadAddress(), address.detailAddress(),
                payment, totalPrice, deliveryFee, deliveryStatus);
    }

    public Long id() {
        return id;
    }

    public Long userId() {
        return userId;
    }

    public PhoneNumber phoneNumber() {
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
        return orderProducts;
    }

    public Address address() {
        return address;
    }

    public LocalDateTime createAt() {
        return createAt;
    }

    public String deliveryStatus() {
        return deliveryStatus;
    }

    public OrderDto toDto(List<OrderProductDto> orderProductDtos) {
        return new OrderDto(id, receiver, phoneNumber.value(),
                payment, totalPrice,deliveryFee,
                deliveryRequest, orderProductDtos,
                address, createAt, deliveryStatus);
    }

    public boolean isDelivered() {
        return this.deliveryStatus.equals(DeliveryStatus.DELIVERED.value());
    }

    public void toDelivered() {
        this.deliveryStatus = DeliveryStatus.DELIVERED.value();
    }

    public void toInTransit() {
        this.deliveryStatus = DeliveryStatus.IN_TRANSIT.value();
    }

    public void toShipped() {
        this.deliveryStatus = DeliveryStatus.SHIPPED.value();
    }
}
