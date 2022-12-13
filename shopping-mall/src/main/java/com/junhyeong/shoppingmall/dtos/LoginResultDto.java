package com.junhyeong.shoppingmall.dtos;

public class LoginResultDto {
    private final String accessToken;
    private final String name;

    public LoginResultDto(String accessToken, String name) {
        this.accessToken = accessToken;
        this.name = name;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getName() {
        return name;
    }
}
