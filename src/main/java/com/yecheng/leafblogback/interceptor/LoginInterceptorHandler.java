package com.yecheng.leafblogback.interceptor;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSigner;
import com.yecheng.leafblogback.utils.AppHttpCodeEnum;
import com.yecheng.leafblogback.utils.ResponseResult;
import com.yecheng.leafblogback.utils.SystemConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Yelf
 * @create 2023-02-17-17:50
 */
@Slf4j
public class LoginInterceptorHandler implements HandlerInterceptor {
    private StringRedisTemplate stringRedisTemplate;

    public LoginInterceptorHandler(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token)) {
            JWT jwt = JWT.of(token);

            boolean verify = jwt.setKey(SystemConstant.TOKEN_KEY.getBytes()).verify();
            if (verify) {
                Object userId = jwt.getPayload("userId");
                String loginVo = stringRedisTemplate.opsForValue().get(SystemConstant.REDIS_TOKEN_KEY + userId);
                if (!StringUtils.hasText(loginVo)){
                    return true;
                }
                JSONObject entries = JSONUtil.parseObj(loginVo);
                String token1 = entries.get("token").toString();
                if (!StringUtils.hasText(loginVo) || !token.equals(token1)) {
                    log.info("LoginInterceptorHandler.preHandle业务结束，结果为：{}","token正确但是redis中已过期");
                    return true;
                }
                long userIdLong = Long.parseLong(userId.toString());
                UserHolderContext.saveUserId(userIdLong);
                log.info("LoginInterceptorHandler.preHandle业务结束，结果为：{}","token正确，且未过期");
            }
        } else {
            log.info("LoginInterceptorHandler.preHandle业务结束，结果为：{}", "无token");
            return true;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolderContext.removeUserId();
    }
}
