package com.junhyeong.shoppingmall.specifications;

import com.junhyeong.shoppingmall.models.Order;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

public class OrderSpecification {
    public static Specification<Order> equalUserId(
            Long userId) {
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("userId"), userId);
            }
        };
    }

    public static Specification<Order> likeProductName(
            String productName) {
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.join("orderProducts").get("productName"), "%" + productName + "%");
            }
        };
    }

    public static Specification<Order> betweenCreatedDatetime(
            LocalDateTime startDate, LocalDateTime endDate) {
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.between(root.get("createAt"), startDate, endDate);
            }
        };
    }
}
