package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.LoginResultDto;
import com.junhyeong.shoppingmall.services.KaKaoService;
import com.junhyeong.shoppingmall.services.LoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class KaKaoLoginController {
    private final KaKaoService kakaoService;
    private final LoginService loginService;

    public KaKaoLoginController(KaKaoService kakaoService, LoginService loginService) {
        this.kakaoService = kakaoService;
        this.loginService = loginService;
    }

    @GetMapping("/auth/token")
    private LoginResultDto kaKaoLogin(@RequestParam("code") String code) {
        String accessToken = kakaoService.getAccessToken(code);

        HashMap<String, Object> userInfo = kakaoService.getUser(accessToken);

        return loginService.kaKaoLogin(userInfo);
    }
}
