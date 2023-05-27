package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.KakaoPayApprovalDto;
import com.junhyeong.shoppingmall.dtos.OrderDto;
import com.junhyeong.shoppingmall.dtos.OrderErrorDto;
import com.junhyeong.shoppingmall.dtos.OrderRequest;
import com.junhyeong.shoppingmall.dtos.OrderRequestDto;
import com.junhyeong.shoppingmall.dtos.OrdersDto;
import com.junhyeong.shoppingmall.exceptions.OptionNotFound;
import com.junhyeong.shoppingmall.exceptions.OrderFailed;
import com.junhyeong.shoppingmall.exceptions.ProductNotFound;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.order.Order;
import com.junhyeong.shoppingmall.models.order.Address;
import com.junhyeong.shoppingmall.models.order.PhoneNumber;
import com.junhyeong.shoppingmall.models.user.UserName;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final CreateOrderService createOrderService;
    private final GetOrdersService getOrdersService;
    private final GetOrderService getOrderService;
    private final KaKaoPay kaKaoPay;

    public OrderController(CreateOrderService createOrderService, GetOrdersService getOrdersService,
                           GetOrderService getOrderService, KaKaoPay kaKaoPay) {
        this.createOrderService = createOrderService;
        this.getOrdersService = getOrdersService;
        this.getOrderService = getOrderService;
        this.kaKaoPay = kaKaoPay;
    }

    @GetMapping
    public OrdersDto orders(
            @RequestAttribute("userName") UserName userName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @PageableDefault(size = 8, page = 0, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable

    ) {
        return getOrdersService.searchOrders(userName, pageable, startDate, endDate);
    }

    @GetMapping("{id}")
    public OrderDto orderDetail(
            @RequestAttribute("userName") UserName userName,
            @PathVariable("id") Long orderId
    ) {
        Order order = getOrderService.orderDetail(orderId);

        OrderDto orderDto = getOrderService.toDto(order);
        return orderDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(
            @RequestAttribute("userName") UserName userName,
            @Validated @RequestBody OrderRequestDto orderRequestDto
    ) {
        try {
            OrderRequest orderRequest = OrderRequest.of(orderRequestDto);

            return createOrderService.createOrder(userName, orderRequest);
        } catch (UserNotFound | ProductNotFound | OptionNotFound e) {
            throw e;
        } catch (Exception e) {
            throw new OrderFailed(e.getMessage());
        }
    }

    @GetMapping("kakaoPaySuccess")
    public KakaoPayApprovalDto orderResult(
            @RequestParam("pg_token") String pgToken
    ) {
        return kaKaoPay.kakaoPayInfo(pgToken).toDto();
    }

    @ExceptionHandler(OrderFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String orderFailed(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(UserNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userNotFound(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(ProductNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String productNotFound(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(OptionNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String optionNotFound(Exception e) {
        return e.getMessage();
    }
}
