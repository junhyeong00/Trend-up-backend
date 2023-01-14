package com.junhyeong.shoppingmall.admin.services;

import com.junhyeong.shoppingmall.dtos.DeliveryInformationDto;
import com.junhyeong.shoppingmall.enums.DeliveryStatus;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GetDeliveryInformationServiceTest {
    @Test
    void deliveryInformation() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        GetDeliveryInformationService getDeliveryInformationService
                = new GetDeliveryInformationService(orderRepository);

        given(orderRepository.countByDeliveryStatus(DeliveryStatus.SHIPPED.value()))
                .willReturn(1L);

        DeliveryInformationDto deliveryInformationDto = getDeliveryInformationService.deliveryInformation();

        assertThat(deliveryInformationDto).isNotNull();

        verify(orderRepository).countByDeliveryStatus(DeliveryStatus.SHIPPED.value());
    }
}