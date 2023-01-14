package com.junhyeong.shoppingmall.admin.services;

import com.junhyeong.shoppingmall.dtos.DeliveryInformationDto;
import com.junhyeong.shoppingmall.enums.DeliveryStatus;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class GetDeliveryInformationService {
    private final OrderRepository orderRepository;

    public GetDeliveryInformationService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public DeliveryInformationDto deliveryInformation() {
        Long shippedCount = orderRepository.countByDeliveryStatus(DeliveryStatus.SHIPPED.value());
        Long inTransitCount = orderRepository.countByDeliveryStatus(DeliveryStatus.IN_TRANSIT.value());
        Long deliveredCount = orderRepository.countByDeliveryStatus(DeliveryStatus.DELIVERED.value());

        return new DeliveryInformationDto(shippedCount, inTransitCount, deliveredCount);
    }
}
