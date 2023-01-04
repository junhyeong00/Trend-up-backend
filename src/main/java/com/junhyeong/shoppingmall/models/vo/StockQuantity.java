package com.junhyeong.shoppingmall.models.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class StockQuantity {
    @Column(name = "stock_quantity")
    private Long value;

    public StockQuantity() {
    }

    public StockQuantity(Long value) {
        this.value = value;
    }

    public Long value() {
        return value;
    }

    @Override
    public String toString() {
        return "StockQuantity(" + value + ")";
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass() == StockQuantity.class &&
                Objects.equals(this.value, ((StockQuantity) other).value);
    }
}
