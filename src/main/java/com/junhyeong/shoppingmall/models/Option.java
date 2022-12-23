package com.junhyeong.shoppingmall.models;

import com.junhyeong.shoppingmall.dtos.OptionDto;
import com.junhyeong.shoppingmall.exceptions.OrderFailed;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Option {
    @Id
    @GeneratedValue
    private Long id;

    private Long productId;

    private String name;

    private Long optionPrice;

    private Long stockQuantity;

    public Option() {
    }

    public Option(Long id, Long productId, String name, Long optionPrice) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.optionPrice = optionPrice;
    }

    public static Option fake(Long optionId) {
        return new Option(optionId, 1L, "두툼한", 10000L);
    }

    public Long id() {
        return id;
    }

    public Long productId() {
        return productId;
    }

    public String name() {
        return name;
    }

    public Long optionPrice() {
        return optionPrice;
    }

    public Long stockQuantity() {
        return stockQuantity;
    }

    public void reduceStock(Long orderQuantity) {
      if (this.stockQuantity < orderQuantity) {
          throw new OrderFailed("재고 부족");
      }

      this.stockQuantity -= orderQuantity;
    }

    public OptionDto toDto() {
        return new OptionDto(id, productId, name, optionPrice);
    }
}
