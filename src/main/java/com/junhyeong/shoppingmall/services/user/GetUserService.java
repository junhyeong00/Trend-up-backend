package com.junhyeong.shoppingmall.services.user;

import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.user.User;
import com.junhyeong.shoppingmall.models.user.UserName;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class GetUserService {
    private UserRepository userRepository;

    public GetUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User user(UserName userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(UserNotFound::new);
    }
}
