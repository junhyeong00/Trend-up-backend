package com.junhyeong.shoppingmall.admin.controllers;

import com.junhyeong.shoppingmall.admin.services.GetOrdersAdminService;
import com.junhyeong.shoppingmall.admin.services.UpdateDeliveryStatusService;
import com.junhyeong.shoppingmall.dtos.DeliveryStatusDto;
import com.junhyeong.shoppingmall.dtos.OrderDto;
import com.junhyeong.shoppingmall.dtos.OrdersDto;
import com.junhyeong.shoppingmall.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminOrderController {
    private final GetOrdersAdminService getOrdersAdminService;
    private final UpdateDeliveryStatusService updateDeliveryStatusService;

    public AdminOrderController(GetOrdersAdminService getOrdersAdminService,
                                UpdateDeliveryStatusService updateDeliveryStatusService) {
        this.getOrdersAdminService = getOrdersAdminService;
        this.updateDeliveryStatusService = updateDeliveryStatusService;
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

    @PatchMapping("admin-orders/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeDeliveryStatus(
            @PathVariable("id") Long orderId,
            @RequestBody DeliveryStatusDto deliveryStatusDto
    ) {
        updateDeliveryStatusService.changeDeliveryStatus(orderId, deliveryStatusDto.getDeliveryStatus());
    }
}
