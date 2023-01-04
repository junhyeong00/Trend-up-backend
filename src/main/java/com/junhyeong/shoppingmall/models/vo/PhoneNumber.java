package com.junhyeong.shoppingmall.models.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class PhoneNumber {
    @Column(name = "phone_number")
    private String value;

    public PhoneNumber() {
    }

    public PhoneNumber(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return "PhoneNumber(" + value + ")";
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass() == PhoneNumber.class &&
                Objects.equals(this.value, ((PhoneNumber) other).value);
    }
}
