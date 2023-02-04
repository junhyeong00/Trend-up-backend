package com.junhyeong.shoppingmall.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RegistrationRequestDto {
    @NotBlank(message = "성함을 입력해주세요.")
    @Pattern(
            regexp = "^[가-힣]{2,10}$",
            message = "2-10자 사이 한글만 사용 가능합니다.")
    private final String name;

    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*\\d)[a-z\\d]{4,16}$",
            message = "영문 소문자/숫자를 포함해 4~16자만 사용 가능합니다.")
    private final String userName;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d(?=.*@$!%*#?&)]{8,}$",
            message = "8글자 이상의 영문(대소문자), 숫자, 특수문자가 모두 포함되어야 합니다.")
    private final String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private final String confirmPassword;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(
            regexp = "^\\d{11}$",
            message = "11자리 전화번호 숫자를 입력해야 합니다. (01012345678)")
    private final String phoneNumber;

    public RegistrationRequestDto(String name, String userName, String password, String confirmPassword, String phoneNumber) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
