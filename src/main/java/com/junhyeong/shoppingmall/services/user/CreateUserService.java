package com.junhyeong.shoppingmall.services.user;

import com.junhyeong.shoppingmall.exceptions.RegisterFailed;
import com.junhyeong.shoppingmall.models.user.User;
import com.junhyeong.shoppingmall.models.user.UserName;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CreateUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String name, UserName userName, String password, String confirmPassword, String phoneNumber) {
        if (userRepository.findByUserName(userName).isPresent()) {
            throw new RegisterFailed(List.of("해당 아이디는 사용할 수 없습니다"));
        }

        if (!password.equals(confirmPassword)) {
            throw new RegisterFailed(List.of("비밀번호가 일치하지 않습니다"));
        }

        User user = new User(userName, name, phoneNumber);
        user.changePassword(password, passwordEncoder);

        return userRepository.save(user);
    }
}
