package com.junhyeong.shoppingmall.services.login;

import com.junhyeong.shoppingmall.dtos.LoginResultDto;
import com.junhyeong.shoppingmall.exceptions.LoginFailed;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import com.junhyeong.shoppingmall.utils.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;


@Service
@Transactional
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User login(UserName userName, String password) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new LoginFailed("존재하지 않는 아이디입니다"));

        if (!user.authenticate(password, passwordEncoder)) {
            throw new LoginFailed("비밀번호가 일치하지 않습니다");
        }

        return user;
    }

    public LoginResultDto kaKaoLogin(HashMap<String, Object> userInfo) {
        String name = String.valueOf(userInfo.get("nickname"));
        UserName userName = new UserName(String.valueOf(userInfo.get("email")));

        if (!userRepository.existsByUserName(userName)) {
            User user = new User(userName, name);

            userRepository.save(user);

            String accessToken = jwtUtil.encode(userName);

            return new LoginResultDto(accessToken, name);
        }

        User user = userRepository.findByUserName(userName)
                .orElseThrow(UserNotFound::new);

        String accessToken = jwtUtil.encode(userName);

        return new LoginResultDto(accessToken, user.name());
    }
}
