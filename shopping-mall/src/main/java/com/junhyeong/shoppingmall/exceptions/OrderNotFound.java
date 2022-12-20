package com.junhyeong.shoppingmall.exceptions;

public class OrderNotFound extends RuntimeException{
    public OrderNotFound() {
        super("존재하지 않는 주문내역입니다");
    }
}
