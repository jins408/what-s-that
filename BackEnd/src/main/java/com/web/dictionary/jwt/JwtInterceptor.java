package com.web.dictionary.jwt;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.web.dictionary.service.JwtService;
import com.web.dictionary.util.UnauthorizedException;

@Component
public class JwtInterceptor implements HandlerInterceptor {
	
	private static final String HEADER_AUTH = "Authorization";
	private static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);
	@Autowired
	private JwtService jwtService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
	

		final String token = request.getHeader(HEADER_AUTH);
		if("OPTIONS".equals(request.getMethod())) {
			logger.debug("if request options method is options, return true");
			return true;
		}
		if(token != null && jwtService.isUsable(token)){
			Map<String,Object> claimsbody = jwtService.getBody(token);
			return true;
		}else{
			throw new UnauthorizedException();
		}
		
	}
	

}
