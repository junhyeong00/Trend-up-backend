package com.junhyeong.shoppingmall.models.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class TotalPrice {
    @Column(name = "total_price")
    private Long value;

    public TotalPrice() {
    }

    public TotalPrice(Long value) {
        this.value = value;
    }

    public Long value() {
        return value;
    }

    @Override
    public String toString() {
        return "TotalPrice(" + value + ")";
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass() == TotalPrice.class &&
                Objects.equals(this.value, ((TotalPrice) other).value);
    }
}
