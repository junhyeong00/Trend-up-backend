package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.models.vo.Cart;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PatchCartServiceTest {
    @Test
    void updateCart() {
        UserRepository userRepository = mock(UserRepository.class);
        PatchCartService patchCartService = new PatchCartService(userRepository);

        UserName userName = new UserName("test123");

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(User.fake(userName)));

        patchCartService.updateCart(userName, new Cart("items"));

        verify(userRepository).findByUserName(userName);
    }
}
