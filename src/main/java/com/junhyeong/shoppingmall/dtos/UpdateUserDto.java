package com.junhyeong.shoppingmall.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UpdateUserDto {
    @NotBlank(message = "이름을 입력해주세요")
    @Pattern(regexp = "^[가-힣]{3,7}$",
            message = "이름을 다시 확인해주세요")
    private String name;

    private String phoneNumber;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d(?=.*@$!%*#?&)]{8,}$",
            message = "비밀번호를 다시 확인해주세요")
    private String password;

    private String confirmPassword;

    public UpdateUserDto(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
