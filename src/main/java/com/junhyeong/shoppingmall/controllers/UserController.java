package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.CartDto;
import com.junhyeong.shoppingmall.dtos.PatchCartDto;
import com.junhyeong.shoppingmall.dtos.UserDto;
import com.junhyeong.shoppingmall.models.Cart;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.UserName;
import com.junhyeong.shoppingmall.services.GetCartService;
import com.junhyeong.shoppingmall.services.GetUserService;
import com.junhyeong.shoppingmall.services.PatchCartService;
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
    private final PatchCartService patchCartService;

    public UserController(GetUserService getUserService,
                          GetCartService getCartService,
                          PatchCartService patchCartService) {
        this.getUserService = getUserService;
        this.getCartService = getCartService;
        this.patchCartService = patchCartService;
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
            @RequestBody PatchCartDto patchCartDto
    ) {
        Cart cart = new Cart(patchCartDto.getItems());

        patchCartService.updateCart(userName, cart);
    }
}
