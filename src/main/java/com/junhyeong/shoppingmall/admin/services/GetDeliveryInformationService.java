package com.junhyeong.shoppingmall.admin.services;

import com.junhyeong.shoppingmall.dtos.DeliveryInformationDto;
import com.junhyeong.shoppingmall.enums.DeliveryStatus;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetDeliveryInformationService {
    private final OrderRepository orderRepository;

    public GetDeliveryInformationService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public DeliveryInformationDto deliveryInformation() {
        Long shippedCount = orderRepository.countByDeliveryStatus(DeliveryStatus.SHIPPED.value());
        Long inTransitCount = orderRepository.countByDeliveryStatus(DeliveryStatus.IN_TRANSIT.value());
        Long deliveredCount = orderRepository.countByDeliveryStatus(DeliveryStatus.DELIVERED.value());

        return new DeliveryInformationDto(shippedCount, inTransitCount, deliveredCount);
    }
}
