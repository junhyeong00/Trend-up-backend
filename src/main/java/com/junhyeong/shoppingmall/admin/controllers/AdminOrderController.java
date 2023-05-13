package com.junhyeong.shoppingmall.admin.controllers;

import com.junhyeong.shoppingmall.admin.services.GetDeliveryInformationService;
import com.junhyeong.shoppingmall.admin.services.GetOrdersAdminService;
import com.junhyeong.shoppingmall.admin.services.GetSalesService;
import com.junhyeong.shoppingmall.admin.services.UpdateDeliveryStatusService;
import com.junhyeong.shoppingmall.dtos.DeliveryInformationDto;
import com.junhyeong.shoppingmall.dtos.DeliveryStatusDto;
import com.junhyeong.shoppingmall.dtos.OrderDto;
import com.junhyeong.shoppingmall.dtos.OrdersDto;
import com.junhyeong.shoppingmall.dtos.SalesDto;
import com.junhyeong.shoppingmall.exceptions.OrderNotFound;
import com.junhyeong.shoppingmall.models.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin/orders")
public class AdminOrderController {
    private final GetOrdersAdminService getOrdersAdminService;
    private final GetSalesService getSalesService;
    private final UpdateDeliveryStatusService updateDeliveryStatusService;
    private final GetDeliveryInformationService getDeliveryInformationService;

    public AdminOrderController(GetOrdersAdminService getOrdersAdminService,
                                GetSalesService getSalesService,
                                UpdateDeliveryStatusService updateDeliveryStatusService,
                                GetDeliveryInformationService getDeliveryInformationService) {
        this.getOrdersAdminService = getOrdersAdminService;
        this.getSalesService = getSalesService;
        this.updateDeliveryStatusService = updateDeliveryStatusService;
        this.getDeliveryInformationService = getDeliveryInformationService;
    }

    @GetMapping
    public OrdersDto orders(
            @PageableDefault(size = 8, page = 0, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Order> orders = getOrdersAdminService.orders(pageable);

        int totalPageCount = orders.getTotalPages();

        List<OrderDto> orderDtos = getOrdersAdminService.toDto(orders);

        return new OrdersDto(orderDtos, totalPageCount);
    }

    @GetMapping("sales")
    public SalesDto sales() {
        return getSalesService.sales();
    }

    @GetMapping("delivery")
    public DeliveryInformationDto deliveryInformation() {
        return getDeliveryInformationService.deliveryInformation();
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeDeliveryStatus(
            @PathVariable("id") Long orderId,
            @RequestBody DeliveryStatusDto deliveryStatusDto
    ) {
        updateDeliveryStatusService.changeDeliveryStatus(orderId, deliveryStatusDto.getDeliveryStatus());
    }

    @ExceptionHandler(OrderNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String orderNotFound(Exception e) {
        return e.getMessage();
    }
}
