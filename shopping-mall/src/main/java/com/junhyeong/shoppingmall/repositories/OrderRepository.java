package com.junhyeong.shoppingmall.repositories;

import com.junhyeong.shoppingmall.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
