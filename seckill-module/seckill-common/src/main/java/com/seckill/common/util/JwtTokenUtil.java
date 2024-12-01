package com.seckill.common.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtTokenUtil {

    // 秘钥
    public static final String SECRETUSER = "5pil6aOO5YaN576O5Lmf5q+U5LiN5LiK5bCP6ZuF55qE56yR";// 用户
    public static final String SECRETADMIN = "ADMIN5pil6aOO5YaN576O5Lmf5q+U5LiN5LiK5bCP6ZuF55qE56yR";// 管理员
    public static final String ALGORITHM = "HmacSHA256";//algorithm

    /***
     * 生成令牌-管理员
     * @param uid:唯一标识符
     * @param ttlMillis:有效期
     * @return
     * @throws Exception
     */
    public static String generateTokenAdmin(String uid, Map<String, Object> payload, long ttlMillis) throws Exception {
        return generateToken(uid, payload, ttlMillis, SECRETADMIN);
    }

    /***
     * 生成令牌-普通用户
     * @param uid:唯一标识符
     * @param ttlMillis:有效期
     * @return
     * @throws Exception
     */
    public static String generateTokenUser(String uid, Map<String, Object> payload, long ttlMillis) throws Exception {
        return generateToken(uid, payload, ttlMillis, SECRETUSER);
    }

    /***
     * 生成令牌
     * @param uid:唯一标识符
     * @param ttlMillis:有效期
     * @return
     */
    public static String generateToken(String uid, Map<String, Object> payload, long ttlMillis, String secret) {

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Key signingKey = new SecretKeySpec(secret.getBytes(), ALGORITHM);

        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        JwtBuilder builder = Jwts.builder().id(uid)
                .issuedAt(now)
                .issuer(uid)
                .subject(uid)
                .header().add(header).and()
                .signWith(signingKey);

        // 设置载体
        builder.claims().add(payload);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.expiration(exp);
        }
        return builder.compact();
    }

    /***
     * 解密JWT令牌
     */
    public static Map<String, Object> parseToken(String token) {
        // 以Bearer开头处理
        if (token.startsWith("Bearer")) {
            token = token.substring(6).trim();
        }

        // 秘钥处理
        SecretKey signingKey = new SecretKeySpec(SECRETUSER.getBytes(), ALGORITHM);

        Claims claims = (Claims) Jwts.parser()
                .verifyWith(signingKey).build()
                .parse(token)
                .getPayload();
        return claims;
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> load = new HashMap<>();
        load.put("abc", "def");
        load.put("中文", "123");
        String token = generateToken(UUID.randomUUID().toString(), load, 900000000L, SECRETUSER);
        System.out.println(token);
        Map<String, Object> parseToken = parseToken(token);
        System.out.println(parseToken);
    }
}



