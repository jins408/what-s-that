package com.web.dictionary.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
//			.addResourceLocations("file:///C:/images/");
                .addResourceLocations("file:///home/ubuntu/springboot/images/");

        System.out.println("resource handler");
    }

    private static final String[] EXCLUDE_PATHS = {
            "/user/**",
            "/swagger-resources/**",
            "/swagger-ui.html/**",
            "/csrf/**",
            "/v2/api-docs/**",
            "/webjars/**",
            "/culture/detail/**",
            "/culture/find/**",
            "/culture/search/**",
            "/culture/favorite/**",
            "/error/**",
            "/comment/load/**",
            "/culture/favorite",
            "/account/userinfo",
            "/images/**",
            "/culture/image/**"
//            ,"/**"
    };

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(EXCLUDE_PATHS);
    }

//	    @Override
//	    public void addCorsMappings(CorsRegistry registry) {
//	        registry.addMapping("/**")
//	        		.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD");
//	    }
}
