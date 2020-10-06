package com.web.dictionary.service;

import com.web.dictionary.util.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    private static final String SALT = "jbk";

    @Autowired
    private JwtService jwtService;

    @Override
    public String createToken(int userno) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        Map<String, Object> payloads = new HashMap<>();
        Long expiredTime = 1000 * 60 * 60 * 24l;//24시간
        Date now = new Date();
        now.setTime(now.getTime() + expiredTime);
        byte[] key = generateKey();
        payloads.put("exp", now);
        payloads.put("userno", userno);
        String jwt = Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
        return jwt;
    }

    @Override
    public boolean isUsable(String jwt) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(this.generateKey())
                    .parseClaimsJws(jwt);
            return true;

        } catch (Exception e) {
            throw new UnauthorizedException();
        }
    }

    private byte[] generateKey() {
        byte[] key = null;
        try {
            key = SALT.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            if (log.isInfoEnabled()) {
                e.printStackTrace();
            } else {
                log.error("Making JWT Key Error ::: {}", e.getMessage());
            }
        }
        return key;
    }

    @Override
    public Map<?, ?> getBody(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwt = request.getHeader("Authorization");
        System.out.println("jwt token : " + jwt);
        Jws<Claims> claims = null;

        try {
            claims = Jwts.parser()
                    .setSigningKey(SALT.getBytes("UTF-8"))
                    .parseClaimsJws(jwt);
        } catch (Exception e) {
            throw new UnauthorizedException();
        }
        return claims.getBody();
    }

    @Override
    public Map<?, ?> getKey(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwt = request.getHeader("Authorization");
        Jws<Claims> claims = null;
        Map<String, Object> map = new HashMap<>();
        if(jwt == null) {
            jwt = "";
        }
        if (!jwt.equals("")) {
            try {
                claims = Jwts.parser()
                        .setSigningKey(SALT.getBytes("UTF-8"))
                        .parseClaimsJws(jwt);
            } catch (Exception e) {
                throw new UnauthorizedException();
            }

            map.put(key, claims.getBody().get(key));
            return map;
        } else {
            return map;
        }
    }

}
