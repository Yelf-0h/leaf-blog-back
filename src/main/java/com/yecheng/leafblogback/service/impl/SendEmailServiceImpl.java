package com.yecheng.leafblogback.service.impl;

import cn.hutool.Hutool;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yecheng.leafblogback.bean.entity.User;
import com.yecheng.leafblogback.service.SendEmailService;
import com.yecheng.leafblogback.service.UserService;
import com.yecheng.leafblogback.utils.AsyncManager;
import com.yecheng.leafblogback.utils.ResponseResult;
import com.yecheng.leafblogback.utils.SystemConstant;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Yelf
 * @create 2023-02-25-18:29
 */
@Service
public class SendEmailServiceImpl implements SendEmailService {
    @Resource
    private UserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 发送电子邮件验证码，注册时
     *
     * @param email 电子邮件
     * @return {@link ResponseResult}<{@link Object}>
     */
    @Override
    public ResponseResult<Object> sendEmailCode(String email) {
        LambdaQueryWrapper<User> userqw = new LambdaQueryWrapper<>();
        userqw.eq(User::getEmail, email);
        long count = userService.count(userqw);
        Long expire = stringRedisTemplate.getExpire(SystemConstant.REDIS_CAPTCHA_KEY + email, TimeUnit.MILLISECONDS);
        if (expire == null || expire < SystemConstant.CAPTCHA_CHECK_TTL) {
            String captcha = RandomUtil.randomNumbers(6);
            if (count > 0) {
                AsyncManager.me().execute(email(email, "凌云博客重置密码", "小伙伴您好！您正在重置密码，请勿泄露您的验证码，您的验证码是：【" + captcha + "】，请尽快使用，有效时间为5分钟！", false));
            } else {
                AsyncManager.me().execute(email(email, "凌云博客注册", "小伙伴您好！欢迎注册凌云博客账号，您的验证码是：【" + captcha + "】，请尽快使用，有效时间为5分钟！", false));
            }
            stringRedisTemplate.opsForValue().set(SystemConstant.REDIS_CAPTCHA_KEY + email, captcha, SystemConstant.REDIS_CAPTCHA_TTL, TimeUnit.MILLISECONDS);
        } else {
            return ResponseResult.errorResult(500, "请勿频繁获取验证码！");
        }
        return ResponseResult.okResult(200, "获取成功！");
    }

    public TimerTask email(String email,String subject,String content,boolean isHtml){
        return new TimerTask() {
            @Override
            public void run() {
                MailUtil.send(email, subject, content, isHtml);
            }
        };
    }
}
