package com.junhyeong.shoppingmall.specifications;

import com.junhyeong.shoppingmall.models.review.Review;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ReviewSpecification {
    public static Specification<Review> equalUserId(
            Long userId) {
        return new Specification<Review>() {
            @Override
            public Predicate toPredicate(Root<Review> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("userId"), userId);
            }
        };
    }

    public static Specification<Review> equalOrderId(
            Long orderId) {
        return new Specification<Review>() {
            @Override
            public Predicate toPredicate(Root<Review> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("orderId"), orderId);
            }
        };
    }

    public static Specification<Review> equalProductId(
            Long productId) {
        return new Specification<Review>() {
            @Override
            public Predicate toPredicate(Root<Review> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("orderProduct").get("productId"), productId);
            }
        };
    }

    public static Specification<Review> equalOptionId(
            Long optionId) {
        return new Specification<Review>() {
            @Override
            public Predicate toPredicate(Root<Review> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("orderProduct").get("optionId"), optionId);
            }
        };
    }

    public static Specification<Review> isFalseDeletedStatus() {
        return new Specification<Review>() {
            @Override
            public Predicate toPredicate(Root<Review> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isFalse(root.get("isDeleted"));
            }
        };
    }
}
