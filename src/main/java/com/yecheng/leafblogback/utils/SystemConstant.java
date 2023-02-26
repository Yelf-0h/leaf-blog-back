package com.yecheng.leafblogback.utils;

/**
 * 系统常量
 *
 * @author Yelf
 * @create 2023-02-17-20:44
 * @date 2023/02/17
 */
public class SystemConstant {
    /**
     * jwt生成令牌的key签名
     */
    public static final String TOKEN_KEY = "yecheng";
    /**
     * redis中,令牌关键字
     */
    public static final String REDIS_TOKEN_KEY = "yecheng:token:";
    /**
     * redis中,验证码关键字
     */
    public static final String REDIS_CAPTCHA_KEY = "yecheng:captcha:";
    public static final String MD5_PASSWORD_SALT = "yecheng:salt:108";
    public static final String OSS_FILE_CLASS = "lyblog/";
    /**
     * redis中,令牌ttl
     */
    public static final Long REDIS_TOKEN_TTL = 1000*60*60*24L;
    /**
     * redis中,验证码ttl
     */
    public static final Long REDIS_CAPTCHA_TTL = 1000*60*5L;
    /**
     * 验证码校验重复发送ttl
     */
    public static final Long CAPTCHA_CHECK_TTL = 1000*60*4L;
}
