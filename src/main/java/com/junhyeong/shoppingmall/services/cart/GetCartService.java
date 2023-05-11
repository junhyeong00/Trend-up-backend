package com.junhyeong.shoppingmall.services.cart;

import com.junhyeong.shoppingmall.dtos.CartDto;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.cart.Cart;
import com.junhyeong.shoppingmall.models.user.User;
import com.junhyeong.shoppingmall.models.user.UserName;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class GetCartService {
    private final UserRepository userRepository;

    public GetCartService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public CartDto cart(UserName userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(UserNotFound::new);

        Cart cart = user.cart();
        return cart.toDto();
    }
}
