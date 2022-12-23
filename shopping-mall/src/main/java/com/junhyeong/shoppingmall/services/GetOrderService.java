package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.dtos.OrderDto;
import com.junhyeong.shoppingmall.dtos.OrderProductDto;
import com.junhyeong.shoppingmall.exceptions.OrderNotFound;
import com.junhyeong.shoppingmall.models.Order;
import com.junhyeong.shoppingmall.models.Review;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import com.junhyeong.shoppingmall.repositories.ReviewRepository;
import com.junhyeong.shoppingmall.specifications.ReviewSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GetOrderService {
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    public GetOrderService(OrderRepository orderRepository, ReviewRepository reviewRepository) {
        this.orderRepository = orderRepository;
        this.reviewRepository = reviewRepository;
    }

    public Order orderDetail(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFound::new);
    }

    public OrderDto toDto(Order order) {
        List<OrderProductDto> orderProductDtos = order.orderProducts().stream()
                .map(orderProduct -> {
                    Specification<Review> spec = Specification.where(ReviewSpecification.equalOrderId(order.id()));
                    spec = spec.and(ReviewSpecification.equalProductId(orderProduct.productId()));
                    spec = spec.and(ReviewSpecification.equalOptionId(orderProduct.optionId()));

                    boolean writable = !reviewRepository.exists(spec) && order.isDelivered();
                    return orderProduct.toOrderProductDto(writable);
                }).toList();

        System.out.println(orderProductDtos);
        return order.toDto(orderProductDtos);
    }
}
