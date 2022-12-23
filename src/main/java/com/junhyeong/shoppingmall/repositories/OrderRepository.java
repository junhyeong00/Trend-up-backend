package com.junhyeong.shoppingmall.repositories;

import com.junhyeong.shoppingmall.models.Order;
import com.junhyeong.shoppingmall.models.UserName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    Page<Order> findAll(Specification<Order> spec, Pageable pageable);
}
