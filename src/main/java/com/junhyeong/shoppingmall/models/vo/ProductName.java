package com.junhyeong.shoppingmall.models.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {
    @Column(name = "product_name")
    private String value;

    public ProductName() {
    }

    public ProductName(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return "ProductName(" + value + ")";
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                other.getClass() == ProductName.class &&
                Objects.equals(this.value, ((ProductName) other).value);
    }
}
