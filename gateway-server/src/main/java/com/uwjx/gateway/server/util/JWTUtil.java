package com.uwjx.gateway.server.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wanghuan
 * @link https://huan.uwjx.com
 * @date 2021/6/26 19:04
 */
@Slf4j
public class JWTUtil {

    public static boolean verifyToken(String token, String secretKey) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            jwtVerifier.verify(token);
        } catch (Exception e) {
            log.warn("token解析失败");
            return false;
        }
        return true;
    }

    public static String getUserInfo(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        String username = decodedJWT.getClaim("user_name").asString();
        Date exp = decodedJWT.getExpiresAt();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            log.warn("过期时间:{}" , format.format(exp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }
}
