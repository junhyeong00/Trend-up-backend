package com.junhyeong.shoppingmall.dtos;

public class UserDto {
    private Long id;
    private String userName;
    private String name;
    private String phoneNumber;

    public UserDto(Long id, String userName, String name, String phoneNumber) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
