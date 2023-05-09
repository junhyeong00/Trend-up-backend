package com.junhyeong.shoppingmall.services.cart;

import com.junhyeong.shoppingmall.dtos.CartDto;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.vo.Cart;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class GetCartService {
    private final UserRepository userRepository;

    public GetCartService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CartDto cart(UserName userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(UserNotFound::new);

        Cart cart = user.cart();
        return cart.toDto();
    }
}
