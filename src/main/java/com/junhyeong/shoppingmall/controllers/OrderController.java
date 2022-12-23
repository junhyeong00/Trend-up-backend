package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.OrderDto;
import com.junhyeong.shoppingmall.dtos.OrderErrorDto;
import com.junhyeong.shoppingmall.dtos.OrderRequestDto;
import com.junhyeong.shoppingmall.dtos.OrderResultDto;
import com.junhyeong.shoppingmall.dtos.OrdersDto;
import com.junhyeong.shoppingmall.exceptions.OrderFailed;
import com.junhyeong.shoppingmall.models.Address;
import com.junhyeong.shoppingmall.models.Order;
import com.junhyeong.shoppingmall.models.PhoneNumber;
import com.junhyeong.shoppingmall.models.UserName;
import com.junhyeong.shoppingmall.services.CreateOrderService;
import com.junhyeong.shoppingmall.services.GetOrderService;
import com.junhyeong.shoppingmall.services.GetOrdersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class OrderController {
    private final CreateOrderService createOrderService;
    private final GetOrdersService getOrdersService;
    private final GetOrderService getOrderService;

    public OrderController(CreateOrderService createOrderService, GetOrdersService getOrdersService, GetOrderService getOrderService) {
        this.createOrderService = createOrderService;
        this.getOrdersService = getOrdersService;
        this.getOrderService = getOrderService;
    }

    @GetMapping("orders")
    public OrdersDto orders(
            @RequestAttribute("userName") UserName userName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
//            @RequestParam(defaultValue = "") String keyword,
            @PageableDefault(size = 8, page = 0, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable

            ) {
        Page<Order> orders = getOrdersService.searchOrders(userName, pageable, startDate, endDate);

        int totalPageCount = orders.getTotalPages();

        List<OrderDto> orderDtos = getOrdersService.toDto(orders);

        return new OrdersDto(orderDtos, totalPageCount);
    }

    @GetMapping("orders/{id}")
    public OrderDto orderDetail(
            @RequestAttribute("userName") UserName userName,
            @PathVariable("id") Long orderId
    ) {
        Order order = getOrderService.orderDetail(orderId);

        OrderDto orderDto = getOrderService.toDto(order);
        return orderDto;
    }

    @PostMapping("order")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResultDto createOrder(
            @RequestAttribute("userName") UserName userName,
            @Validated @RequestBody OrderRequestDto orderRequestDto, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList().get(0);
            throw new OrderFailed(errorMessage);
        }

        Address address = new Address(
                orderRequestDto.getZipCode(),
                orderRequestDto.getRoadAddress(),
                orderRequestDto.getDetailAddress());

        PhoneNumber phoneNumber = new PhoneNumber(orderRequestDto.getPhoneNumber());

        Order order = createOrderService.createOrder(
                userName,
                phoneNumber,
                orderRequestDto.getReceiver(),
                orderRequestDto.getPayment(),
                orderRequestDto.getTotalPrice(),
                orderRequestDto.getDeliveryFee(),
                orderRequestDto.getOrderProducts(),
                orderRequestDto.getDeliveryRequest(),
                address
        );

        return order.toOrderResultDto();
    }

    @ExceptionHandler(OrderFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public OrderErrorDto orderFailed(OrderFailed e) {
        return new OrderErrorDto(e.getErrorMessage());
    }
}
