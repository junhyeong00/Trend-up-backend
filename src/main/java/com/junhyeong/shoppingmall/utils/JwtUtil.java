package com.junhyeong.shoppingmall.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.junhyeong.shoppingmall.models.vo.UserName;

public class JwtUtil {
    private final Algorithm algorithm;

    public JwtUtil(String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String encode(UserName userName) {
        return JWT.create()
                .withClaim("userName", userName.value())
                .sign(algorithm);
    }

    public UserName decode(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT verify = verifier.verify(token);
        String value = verify.getClaim("userName").asString();
        return new UserName(value);
    }
}
