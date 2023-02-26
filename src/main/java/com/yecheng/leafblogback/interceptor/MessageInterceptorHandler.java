package com.yecheng.leafblogback.interceptor;

import com.yecheng.leafblogback.exception.UnAuthenticationException;
import com.yecheng.leafblogback.utils.SystemConstant;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author Yelf
 * @create 2023-02-22-15:35
 */

public class MessageInterceptorHandler implements HandlerInterceptor {
    private StringRedisTemplate stringRedisTemplate;

    public MessageInterceptorHandler(StringRedisTemplate redisTemplate){
        this.stringRedisTemplate=redisTemplate;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token)){
            throw new UnAuthenticationException();
        }
        Long userId = UserHolderContext.getUserId();
        if (Objects.isNull(userId)){
            throw new UnAuthenticationException();
        }
        String s = stringRedisTemplate.opsForValue().get(SystemConstant.REDIS_TOKEN_KEY + userId);
        if (!StringUtils.hasText(s)){
            throw new UnAuthenticationException();
        }
        return true;
    }
}
