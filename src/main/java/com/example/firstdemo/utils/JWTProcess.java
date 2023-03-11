package com.example.firstdemo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
@Component
public class JWTProcess {

    private static String secret;

    private static Integer expire;

    @Value("${token-expired-in}")
    public void setExpire(Integer expire) {
        JWTProcess.expire = expire;
    }
    @Value("${jwt-key}")
    public void setSecret(String secret) {
        JWTProcess.secret = secret;
    }

    public static String createJWT(String userId, String username) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, expire);
        Date expire = calendar.getTime();
        return JWT.create()
                .withExpiresAt(expire)
                .withClaim("userId", userId)
                .sign(algorithm);
    }

    public static String verifyJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .build()
                .verify(token)
                .getClaim("userId")
                .asString();
    }

}
