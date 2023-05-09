package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.dtos.CartDto;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import com.junhyeong.shoppingmall.services.cart.GetCartService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GetCartServiceTest {
    @Test
    void cart() {
        UserRepository userRepository = mock(UserRepository.class);
        GetCartService getCartService = new GetCartService(userRepository);

        UserName userName = new UserName("test123");

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(User.fake(userName)));

        CartDto cartDto = getCartService.cart(userName);

        assertThat(cartDto).isNotNull();

        verify(userRepository).findByUserName(userName);
    }
}
