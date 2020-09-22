package com.web.dictionary.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.web.dictionary.service.JwtService;
import com.web.dictionary.util.UnauthorizedException;

@Component
public class JwtInterceptor implements HandlerInterceptor {
	
	private static final String HEADER_AUTH = "Authorization";

	@Autowired
	private JwtService jwtService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		final String token = request.getHeader(HEADER_AUTH);

		if(token != null && jwtService.isUsable(token)){
//			Date now = new Date();
//			now.setTime(now.getTime()+1l);
//			Map<String,Object> timeconvert = new HashMap<>();
//			timeconvert.put("now", now);
//			System.out.println("now!!!! " +timeconvert.get("now"));
			Map<String,Object> claimsbody = jwtService.getBody(token);
			System.out.println("exp "+claimsbody.get("exp"));
			return true;
		}else{
			throw new UnauthorizedException();
		}
	}
	

}
