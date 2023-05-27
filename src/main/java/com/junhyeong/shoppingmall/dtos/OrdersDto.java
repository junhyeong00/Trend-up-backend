package com.junhyeong.shoppingmall.dtos;

import java.util.List;

public class OrdersDto {
    private List<OrderDto> orders;
    private int totalPageCount;
    private long shippedCount;
    private long inTransitCount;

    public OrdersDto(List<OrderDto> orders, int totalPageCount, long shippedCount, long inTransitCount) {
        this.orders = orders;
        this.totalPageCount = totalPageCount;
        this.shippedCount = shippedCount;
        this.inTransitCount = inTransitCount;
    }

    public OrdersDto(List<OrderDto> orders, int totalPageCount) {
        this.orders = orders;
        this.totalPageCount = totalPageCount;
    }

    public List<OrderDto> getOrders() {
        return orders;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public long getShippedCount() {
        return shippedCount;
    }

    public long getInTransitCount() {
        return inTransitCount;
    }
}
