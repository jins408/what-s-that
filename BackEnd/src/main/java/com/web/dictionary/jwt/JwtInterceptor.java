package com.web.dictionary.jwt;

import com.web.dictionary.service.JwtService;
import com.web.dictionary.util.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final String HEADER_AUTH = "Authorization";
    private static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);
    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String uri = request.getRequestURI();
        logger.info("In Interceptor ({})", uri);

        final String token = request.getHeader(HEADER_AUTH);
        logger.info("token : {}", token);

        Enumeration headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String headerName = (String) headers.nextElement();
            String value = request.getHeader(headerName);
        }

        if ("OPTIONS".equals(request.getMethod())) {
            logger.debug("if request options method is options, return true");
            return true;
        }
        if (token != null && jwtService.isUsable(token)) {
            return true;
        } else {
            logger.info("throw new UnauthorizedException()");
            throw new UnauthorizedException();
        }

    }


}
