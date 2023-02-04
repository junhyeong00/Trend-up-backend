package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.LoginResultDto;
import com.junhyeong.shoppingmall.services.KaKaoLoginService;
import com.junhyeong.shoppingmall.services.LoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class KaKaoLoginController {
    private final KaKaoLoginService kakaoLoginService;
    private final LoginService loginService;

    public KaKaoLoginController(KaKaoLoginService kakaoLoginService, LoginService loginService) {
        this.kakaoLoginService = kakaoLoginService;
        this.loginService = loginService;
    }

    @GetMapping("/auth/token")
    private LoginResultDto kaKaoLogin(@RequestParam("code") String code) {
        String accessToken = kakaoLoginService.getAccessToken(code);

        HashMap<String, Object> userInfo = kakaoLoginService.getUser(accessToken);

        System.out.println("===");
        System.out.println(userInfo);
        System.out.println("===");
        return loginService.kaKaoLogin(userInfo);
    }
}
