package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.KakaoPayApprovalDto;
import com.junhyeong.shoppingmall.dtos.OrderDto;
import com.junhyeong.shoppingmall.dtos.OrderErrorDto;
import com.junhyeong.shoppingmall.dtos.OrderRequestDto;
import com.junhyeong.shoppingmall.dtos.OrdersDto;
import com.junhyeong.shoppingmall.exceptions.OrderFailed;
import com.junhyeong.shoppingmall.models.Order;
import com.junhyeong.shoppingmall.models.vo.Address;
import com.junhyeong.shoppingmall.models.vo.PhoneNumber;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.services.order.CreateOrderService;
import com.junhyeong.shoppingmall.services.order.GetOrderService;
import com.junhyeong.shoppingmall.services.order.GetOrdersService;
import com.junhyeong.shoppingmall.utils.KaKaoPay;
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

@RestController
public class OrderController {
    private final CreateOrderService createOrderService;
    private final GetOrdersService getOrdersService;
    private final GetOrderService getOrderService;
    private final KaKaoPay kaKaoPay;

    public OrderController(CreateOrderService createOrderService, GetOrdersService getOrdersService, GetOrderService getOrderService, KaKaoPay kaKaoPay) {
        this.createOrderService = createOrderService;
        this.getOrdersService = getOrdersService;
        this.getOrderService = getOrderService;
        this.kaKaoPay = kaKaoPay;
    }

    @GetMapping("orders")
    public OrdersDto orders(
            @RequestAttribute("userName") UserName userName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @PageableDefault(size = 8, page = 0, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable

    ) {
        return getOrdersService.searchOrders(userName, pageable, startDate, endDate);
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
    public String createOrder(
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

        return createOrderService.createOrder(
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
    }

    @GetMapping("orders/kakaoPaySuccess")
    public KakaoPayApprovalDto orderResult(
            @RequestParam("pg_token") String pgToken
    ) {
        return kaKaoPay.kakaoPayInfo(pgToken).toDto();
    }

    @ExceptionHandler(OrderFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public OrderErrorDto orderFailed(OrderFailed e) {
        return new OrderErrorDto(e.getErrorMessage());
    }
}
