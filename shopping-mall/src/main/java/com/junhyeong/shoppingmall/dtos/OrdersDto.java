package com.junhyeong.shoppingmall.dtos;

import org.springframework.data.domain.Page;

import java.util.List;

public class OrdersDto {
    private List<OrderDto> orders;
    private int totalPageCount;

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
}
