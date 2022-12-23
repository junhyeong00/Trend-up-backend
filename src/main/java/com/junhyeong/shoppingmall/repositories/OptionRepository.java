package com.junhyeong.shoppingmall.repositories;

import com.junhyeong.shoppingmall.models.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByProductId(Long productId);
}
