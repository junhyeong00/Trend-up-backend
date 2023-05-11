package com.junhyeong.shoppingmall.services.cart;

import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.cart.Cart;
import com.junhyeong.shoppingmall.models.user.User;
import com.junhyeong.shoppingmall.models.user.UserName;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UpdateCartService {
    private final UserRepository userRepository;

    public UpdateCartService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void updateCart(UserName userName, Cart cart) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(UserNotFound::new);

        user.updateCart(cart);
    }
}
