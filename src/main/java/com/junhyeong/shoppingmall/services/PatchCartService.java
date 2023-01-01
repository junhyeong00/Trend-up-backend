package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.Cart;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.UserName;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PatchCartService {
    private final UserRepository userRepository;

    public PatchCartService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateCart(UserName userName, Cart cart) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(UserNotFound::new);

        user.updateCart(cart);
    }
}
