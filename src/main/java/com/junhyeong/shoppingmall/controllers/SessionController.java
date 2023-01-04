package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.LoginErrorDto;
import com.junhyeong.shoppingmall.dtos.LoginRequestDto;
import com.junhyeong.shoppingmall.dtos.LoginResultDto;
import com.junhyeong.shoppingmall.exceptions.LoginFailed;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.services.LoginService;
import com.junhyeong.shoppingmall.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("session")
public class SessionController {
    private final LoginService loginService;
    private final JwtUtil jwtUtil;

    public SessionController(LoginService loginService, JwtUtil jwtUtil) {
        this.loginService = loginService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResultDto login(
            @Validated @RequestBody LoginRequestDto loginRequestDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList().get(0);
            throw new LoginFailed(errorMessage);
        }

        UserName userName = new UserName(loginRequestDto.getUserName());
        String password = loginRequestDto.getPassword();

        User user = loginService.login(userName, password);

        String accessToken = jwtUtil.encode(userName);

        return new LoginResultDto(accessToken, user.name());
    }

    @ExceptionHandler(LoginFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public LoginErrorDto loginFailed(LoginFailed e) {
        return new LoginErrorDto(e.getMessage());
    }
}
