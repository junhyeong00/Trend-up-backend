package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.models.cart.Cart;
import com.junhyeong.shoppingmall.models.user.User;
import com.junhyeong.shoppingmall.models.user.UserName;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import com.junhyeong.shoppingmall.services.cart.UpdateCartService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UpdateCartServiceTest {
    @Test
    void updateCart() {
        UserRepository userRepository = mock(UserRepository.class);
        UpdateCartService updateCartService = new UpdateCartService(userRepository);

        UserName userName = new UserName("test123");

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(User.fake(userName)));

        updateCartService.updateCart(userName, new Cart("items"));

        verify(userRepository).findByUserName(userName);
    }
}
