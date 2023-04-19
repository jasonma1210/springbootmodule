package com.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

public class JWTUtil01 {

    // 有效期 24小时
    public static final Long JWT_TTL = 1000 * 60 * 60 * 24L;

    // JWT令牌信息
    public static final String JWT_KEY = "majian";

    public static String createJWT(String id,String username, String subject, Long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        if (ttlMillis == null) {
            ttlMillis = JWT_TTL;
        }

        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);

        SecretKey secretKey = generalKey();

        JwtBuilder builder = Jwts.builder()
                .setId(id)                      // 设置唯一编号
                .setSubject(subject)            // 设置主题 可以是JSON数据
                .setIssuer("jason")
                .setIssuedAt(now)               // 设置签发日期
                .setExpiration(expDate)         // 设置过期时间
                .claim("id",id)
                .claim("username",username)
                // 设置签名 使用HS256算法 并设置SecretKey(字符串)
                .signWith(SignatureAlgorithm.HS256, secretKey);
        return builder.compact();


    }

    /**
     * 生成加密secretKey
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getEncoder().encode(JWT_KEY.getBytes());
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 解析令牌数据
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    /**
     * 判断token是否有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(null == jwtToken || "".equals(jwtToken)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        System.out.println(checkToken("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoibG9naW5Vc2VyIiwiaXNzIjoiamFzb24iLCJpYXQiOjE2NjAxODY5MDgsImV4cCI6MTY2MDI3MzMwOCwiaWQiOiIxIiwidXNlcm5hbWUiOiJ1c2VyMSJ9.XjTf8sxxh_V2vzHx6FbazYUZch3PliFxjAEBJKZDY5g"));
    }

}