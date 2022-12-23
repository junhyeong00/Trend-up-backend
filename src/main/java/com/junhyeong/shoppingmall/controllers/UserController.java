package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.UserDto;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.UserName;
import com.junhyeong.shoppingmall.services.GetUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    private GetUserService getUserService;

    public UserController(GetUserService getUserService) {
        this.getUserService = getUserService;
    }

    @GetMapping("me")
    public UserDto user(
            @RequestAttribute("userName") UserName userName
            ) {
        User user = getUserService.user(userName);
        return user.toDto();
    }
}
