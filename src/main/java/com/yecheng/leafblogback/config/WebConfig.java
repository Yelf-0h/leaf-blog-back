package com.yecheng.leafblogback.config;

import com.yecheng.leafblogback.interceptor.LoginInterceptorHandler;
import com.yecheng.leafblogback.interceptor.MessageInterceptorHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author Yelf
 * @create 2023-02-17-19:29
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptorHandler(stringRedisTemplate))
                .addPathPatterns("/**")
                .order(0);
        registry.addInterceptor(new MessageInterceptorHandler(stringRedisTemplate))
                .addPathPatterns("/comment/**")
                .excludePathPatterns("/comment/message")
                .order(1);
    }
}
