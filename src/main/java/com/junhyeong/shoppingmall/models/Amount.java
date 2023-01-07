package com.junhyeong.shoppingmall.models;

import java.util.Objects;

public class Amount {
    private Integer total;

    private Integer tax_free;

    private Integer vat;

    private Integer point;

    private Integer discount;

    private Integer green_deposit;

    public Amount() {
    }

    public Amount(
            Integer total,
            Integer tax_free,
            Integer vat,
            Integer point,
            Integer discount,
            Integer green_deposit) {
        this.total = total;
        this.tax_free = tax_free;
        this.vat = vat;
        this.point = point;
        this.discount = discount;
        this.green_deposit = green_deposit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount amount = (Amount) o;
        return Objects.equals(total, amount.total) && Objects.equals(tax_free, amount.tax_free) && Objects.equals(vat, amount.vat) && Objects.equals(point, amount.point) && Objects.equals(discount, amount.discount) && Objects.equals(green_deposit, amount.green_deposit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(total, tax_free, vat, point, discount, green_deposit);
    }

    @Override
    public String toString() {
        return "Amount{" +
                "total=" + total +
                ", tax_free=" + tax_free +
                ", vat=" + vat +
                ", point=" + point +
                ", discount=" + discount +
                ", green_deposit=" + green_deposit +
                '}';
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getTax_free() {
        return tax_free;
    }

    public Integer getVat() {
        return vat;
    }

    public Integer getPoint() {
        return point;
    }

    public Integer getDiscount() {
        return discount;
    }

    public Integer getGreen_deposit() {
        return green_deposit;
    }
}
