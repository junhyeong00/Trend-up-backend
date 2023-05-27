package com.junhyeong.shoppingmall.models.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class UserName {
    @Column(name = "user_name")
    private String value;

    public UserName() {
    }

    public UserName(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return "UserName(" + value + ")";
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass() == UserName.class &&
                Objects.equals(this.value, ((UserName) other).value);
    }
}
