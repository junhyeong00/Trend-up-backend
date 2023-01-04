package com.junhyeong.shoppingmall.models.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductId {
    @Column(name = "product_id")
    private Long value;

    public ProductId() {
    }

    public ProductId(Long value) {
        this.value = value;
    }

    public Long value() {
        return value;
    }

    @Override
    public String toString() {
        return "ProductId(" + value + ")";
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass() == ProductId.class &&
                Objects.equals(this.value, ((ProductId) other).value);
    }
}
