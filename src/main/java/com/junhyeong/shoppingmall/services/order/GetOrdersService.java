package com.junhyeong.shoppingmall.services.order;

import com.junhyeong.shoppingmall.dtos.OrderDto;
import com.junhyeong.shoppingmall.dtos.OrderProductDto;
import com.junhyeong.shoppingmall.dtos.OrdersDto;
import com.junhyeong.shoppingmall.enums.DeliveryStatus;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.order.Order;
import com.junhyeong.shoppingmall.models.review.Review;
import com.junhyeong.shoppingmall.models.user.User;
import com.junhyeong.shoppingmall.models.user.UserName;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import com.junhyeong.shoppingmall.repositories.ReviewRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import com.junhyeong.shoppingmall.specifications.OrderSpecification;
import com.junhyeong.shoppingmall.specifications.ReviewSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GetOrdersService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public GetOrdersService(OrderRepository orderRepository, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @Transactional(readOnly = true)
    public OrdersDto searchOrders(UserName userName, Pageable pageable,
                                    LocalDateTime startDate, LocalDateTime endDate) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(()-> new UserNotFound());

        Specification<Order> spec = Specification.where(OrderSpecification.equalUserId(user.id()));

        if(startDate != null && endDate != null) {
            spec = spec.and(OrderSpecification.betweenCreatedDatetime(
                    startDate.plusDays(1).withHour(0), endDate.plusDays(1).withHour(23).withMinute(59)));
        }

        Page<Order> orders = orderRepository.findAll(spec, pageable);

        int totalPageCount = orders.getTotalPages();

        List<OrderDto> orderDtos = toDto(orders);

        long shippedCount = calcShippedCount(user.id());
        long inTransitCount = calcInTransitCount(user.id());

        return new OrdersDto(orderDtos, totalPageCount, shippedCount, inTransitCount);
    }

    private long calcInTransitCount(Long userId) {
        Specification<Order> spec = Specification.where(OrderSpecification.equalUserId(userId));
        spec = spec.and(OrderSpecification.equalDeliveryStatus(DeliveryStatus.IN_TRANSIT.value()));
        long inTransitCount = orderRepository.count(spec);
        return inTransitCount;
    }

    private long calcShippedCount(Long userId) {
        Specification<Order> spec = Specification.where(OrderSpecification.equalUserId(userId));
        spec = spec.and(OrderSpecification.equalDeliveryStatus(DeliveryStatus.SHIPPED.value()));
        long shippedCount = orderRepository.count(spec);
        return shippedCount;
    }

    public List<OrderDto> toDto(Page<Order> orders) {
        return orders.stream().map(order -> {
            List<OrderProductDto> orderProductDtos = order.orderProducts().stream()
                    .map(orderProduct -> {

                        Specification<Review> spec = Specification.where(ReviewSpecification.equalOrderId(order.id()));
                        spec = spec.and(ReviewSpecification.equalProductId(orderProduct.productId()));
                        spec = spec.and(ReviewSpecification.equalOptionId(orderProduct.optionId()));

                        boolean writable = !reviewRepository.exists(spec) && order.isDelivered();
                        return orderProduct.toOrderProductDto(writable);
                    }).toList();

            return order.toDto(orderProductDtos);
        }).toList();
    }
}
