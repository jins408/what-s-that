package com.web.dictionary.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.web.dictionary.util.UnauthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

	private static final String SALT =  "jbk";
	
	@Autowired
	private JwtService jwtService;

	
	@Override
	public String createToken(int userno) {
		Map<String, Object> headers = new HashMap<>();
		headers.put("typ", "JWT");
		headers.put("alg", "HS256");
	    
		Map<String, Object> payloads = new HashMap<>();
		Long expiredTime = 1000 * 60l;//1분
		Date now = new Date();
		System.out.println("now "+now);
		now.setTime(now.getTime() + expiredTime);
		byte[] key = generateKey();
		System.out.println("create token time" + now);
		payloads.put("exp", now);
		System.out.println("payload exp " +payloads.get("exp"));
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
		try{
			Jws<Claims> claims = Jwts.parser()
					  .setSigningKey(this.generateKey())
					  .parseClaimsJws(jwt);
			return true;
			
		}catch (Exception e) {
			throw new UnauthorizedException();
		}
	}

	private byte[] generateKey() {
		byte[] key = null;
		try {
			key = SALT.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			if(log.isInfoEnabled()){
				e.printStackTrace();
			}else{
				log.error("Making JWT Key Error ::: {}", e.getMessage());
			}
		}
		return key;
	}
	
	@Override
	public Map<String, Object> getBody(String token) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String jwt = request.getHeader("Authorization");
		System.out.println("jwt "+jwt);
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser()
						 .setSigningKey(SALT.getBytes("UTF-8"))
						 .parseClaimsJws(jwt);
			System.out.println("claims "+ claims);
		} catch (Exception e) {
			throw new UnauthorizedException();
		}
//		@SuppressWarnings("unchecked")
//		Map<String, Object> value = (LinkedHashMap<String, Object>)claims.getBody().get(token);
		return claims.getBody();
	}

	
	
	
	
}
