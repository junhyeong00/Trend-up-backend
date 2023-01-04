package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.models.Option;
import com.junhyeong.shoppingmall.repositories.OptionRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GetOptionsServiceTest {
    @Test
    void options() {
        OptionRepository optionRepository = mock(OptionRepository.class);
        GetOptionsService getOptionsService = new GetOptionsService(optionRepository);

        Long productId = 1L;
        given(optionRepository.findByProductId(productId))
                .willReturn(List.of(
                        new Option(1L, 1L, "기본", 10000L),
                        new Option(2L, 1L, "두툼한", 10000L)
                ));

        List<Option> founds = getOptionsService.options(productId);

        assertThat(founds).hasSize(2);

        verify(optionRepository).findByProductId(productId);
    }
}