package com.example.util;

import com.example.dto.TUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Set;

public class JWTUtil {

    // 有效期 24小时
    public static final Long JWT_TTL = 1000 * 60 * 60 * 24L;

    // JWT令牌信息
    public static final String JWT_KEY = "majianjasonmajianjason";

    public static String createJWT(String id, TUser user, String subject, Long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        if (ttlMillis == null) {
            ttlMillis = JWT_TTL;
        }

        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);

        SecretKey secretKey = generalKey();
            user.setPass("PROTECTED");
        JwtBuilder builder = Jwts.builder()
                .setId(id)                      // 设置唯一编号
                .setSubject(subject)            // 设置主题 可以是JSON数据
                .setIssuer("jason")
                .setIssuedAt(now)               // 设置签发日期
                .setExpiration(expDate)         // 设置过期时间
                //payload
                .claim("user",user)
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


    public static void main(String[] args) throws Exception {
//                TUser user = new TUser();
//                user.setId(1);
//                user.setName("majian");
//                user.setPass("123456");
//        System.out.println(createJWT(String.valueOf(user.getId()),user,"jwtlogin",null));
        Claims claims = parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoiand0bG9naW4iLCJpc3MiOiJqYXNvbiIsImlhdCI6MTY4MTI3OTMyMSwiZXhwIjoxNjgxMzY1NzIxLCJ1c2VyIjp7ImlkIjoxLCJuYW1lIjoibWFqaWFuIiwicGFzcyI6IlBST1RFQ1RFRCJ9fQ.COUvQvh8VKUivRzgk8a_psm1ZIxFdKDaZpUmSPoK2XE");
      LinkedHashMap<String,Object> map = ( LinkedHashMap<String,Object>) claims.get("user");
        Set<String> keys = map.keySet();
        for(String key : keys){
            System.out.println(map.get(key));
        }

    }

}