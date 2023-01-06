package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.CartDto;
import com.junhyeong.shoppingmall.dtos.UpdateCartDto;
import com.junhyeong.shoppingmall.dtos.UserDto;
import com.junhyeong.shoppingmall.models.vo.Cart;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.services.GetCartService;
import com.junhyeong.shoppingmall.services.GetUserService;
import com.junhyeong.shoppingmall.services.UpdateCartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    private final GetUserService getUserService;
    private final GetCartService getCartService;
    private final UpdateCartService updateCartService;

    public UserController(GetUserService getUserService,
                          GetCartService getCartService,
                          UpdateCartService updateCartService) {
        this.getUserService = getUserService;
        this.getCartService = getCartService;
        this.updateCartService = updateCartService;
    }

    @GetMapping("me")
    public UserDto user(
            @RequestAttribute("userName") UserName userName
            ) {
        User user = getUserService.user(userName);
        return user.toDto();
    }

    @GetMapping("cart")
    public CartDto cart(
            @RequestAttribute("userName") UserName userName
    ) {
        return getCartService.cart(userName);
    }

    @PatchMapping("cart")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCart(
            @RequestAttribute("userName") UserName userName,
            @RequestBody UpdateCartDto updateCartDto
    ) {
        Cart cart = new Cart(updateCartDto.getItems());

        updateCartService.updateCart(userName, cart);
    }
}
