package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.exceptions.LoginFailed;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User login(UserName userName, String password) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new LoginFailed("존재하지 않는 아이디입니다"));

        if (!user.authenticate(password, passwordEncoder)) {
            throw new LoginFailed("비밀번호가 일치하지 않습니다");
        }

        return user;
    }
}
