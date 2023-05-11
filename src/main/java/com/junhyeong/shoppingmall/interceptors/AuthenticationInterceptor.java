package com.junhyeong.shoppingmall.interceptors;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.junhyeong.shoppingmall.exceptions.AuthenticationError;
import com.junhyeong.shoppingmall.models.user.UserName;
import com.junhyeong.shoppingmall.utils.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthenticationInterceptor implements HandlerInterceptor {
    private JwtUtil jwtUtil;

    public AuthenticationInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return true;
        }

        String accessToken = authorization.substring("Bearer ".length());

        try {
            UserName userName = jwtUtil.decode(accessToken);
            request.setAttribute("userName", userName);
            return true;
        } catch (JWTDecodeException exception) {
            throw new AuthenticationError();
        }
    }
}
