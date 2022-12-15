package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.OrderErrorDto;
import com.junhyeong.shoppingmall.dtos.OrderRequestDto;
import com.junhyeong.shoppingmall.dtos.OrderResultDto;
import com.junhyeong.shoppingmall.exceptions.LoginFailed;
import com.junhyeong.shoppingmall.exceptions.OrderFailed;
import com.junhyeong.shoppingmall.models.Address;
import com.junhyeong.shoppingmall.models.Order;
import com.junhyeong.shoppingmall.models.PhoneNumber;
import com.junhyeong.shoppingmall.models.UserName;
import com.junhyeong.shoppingmall.services.CreateOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private final CreateOrderService createOrderService;

    public OrderController(CreateOrderService createOrderService) {
        this.createOrderService = createOrderService;
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
