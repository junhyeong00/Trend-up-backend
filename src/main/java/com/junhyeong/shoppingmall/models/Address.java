package com.junhyeong.shoppingmall.models;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address {
    private Long zipCode;
    private String roadAddress;
    private String detailAddress;

    public Address() {
    }

    public Address(Long zipCode, String roadAddress, String detailAddress) {
        this.zipCode = zipCode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(zipCode, address.zipCode) &&
                Objects.equals(roadAddress, address.roadAddress) &&
                Objects.equals(detailAddress, address.detailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zipCode, roadAddress, detailAddress);
    }

    public Long zipCode() {
        return zipCode;
    }

    public String roadAddress() {
        return roadAddress;
    }

    public String detailAddress() {
        return detailAddress;
    }
}
