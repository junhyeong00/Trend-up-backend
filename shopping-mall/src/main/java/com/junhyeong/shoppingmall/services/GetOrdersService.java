package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.Order;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.UserName;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import com.junhyeong.shoppingmall.specifications.OrderSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class GetOrdersService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public GetOrdersService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public Page<Order> searchOrders(UserName userName, Pageable pageable,
                                    LocalDateTime startDate, LocalDateTime endDate) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(()-> new UserNotFound());

        Specification<Order> spec = Specification.where(OrderSpecification.equalUserId(user.id()));

        if(startDate != null && endDate != null) {
            spec = spec.and(OrderSpecification.betweenCreatedDatetime(startDate, endDate));
        }

//        if(keyword != null) {
//            spec = spec.and(OrderSpecification.likeProductName(keyword));
//        }

        return orderRepository.findAll(spec, pageable);
    }
}
