package com.junhyeong.shoppingmall.dtos;

public class UserDto {
    private Long id;
    private String userName;
    private String name;
    private String phoneNumber;
    private boolean isSnsUser;

    public UserDto(Long id, String userName, String name, String phoneNumber, boolean isSnsUser) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.isSnsUser = isSnsUser;
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

    public boolean isSnsUser() {
        return isSnsUser;
    }
}
