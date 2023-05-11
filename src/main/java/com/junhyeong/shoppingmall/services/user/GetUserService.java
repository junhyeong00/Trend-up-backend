package com.junhyeong.shoppingmall.services.user;

import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.user.User;
import com.junhyeong.shoppingmall.models.user.UserName;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class GetUserService {
    private UserRepository userRepository;

    public GetUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User user(UserName userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(UserNotFound::new);
    }
}
