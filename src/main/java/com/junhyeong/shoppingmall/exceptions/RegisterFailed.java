package com.junhyeong.shoppingmall.exceptions;

import java.util.List;

public class RegisterFailed extends RuntimeException {
    private List<String> errorMessages;

    public RegisterFailed(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public List<String> errorMessages() {
        return errorMessages;
    }
}
