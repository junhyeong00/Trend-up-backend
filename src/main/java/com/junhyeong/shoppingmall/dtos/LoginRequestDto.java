package com.junhyeong.shoppingmall.dtos;

import javax.validation.constraints.NotBlank;

public class LoginRequestDto {
    @NotBlank(message = "아이디를 입력해주세요")
    private String userName;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    public LoginRequestDto(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
