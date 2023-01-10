package com.junhyeong.shoppingmall.admin.controllers;

import com.junhyeong.shoppingmall.admin.services.GetOrdersAdminService;
import com.junhyeong.shoppingmall.dtos.OrderDto;
import com.junhyeong.shoppingmall.dtos.OrdersDto;
import com.junhyeong.shoppingmall.models.Order;
import com.junhyeong.shoppingmall.models.vo.UserName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class AdminOrderController {
    private final GetOrdersAdminService getOrdersAdminService;

    public AdminOrderController(GetOrdersAdminService getOrdersAdminService) {
        this.getOrdersAdminService = getOrdersAdminService;
    }

    @GetMapping("admin-orders")
    public OrdersDto orders(
            @PageableDefault(size = 8, page = 0, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Order> orders = getOrdersAdminService.orders(pageable);

        int totalPageCount = orders.getTotalPages();

        List<OrderDto> orderDtos = getOrdersAdminService.toDto(orders);

        return new OrdersDto(orderDtos, totalPageCount);
    }
}
