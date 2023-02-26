package com.yecheng.leafblogback.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.yecheng.leafblogback.bean.dto.*;
import com.yecheng.leafblogback.bean.vo.LoginVo;
import com.yecheng.leafblogback.interceptor.UserHolderContext;
import com.yecheng.leafblogback.utils.AppHttpCodeEnum;
import com.yecheng.leafblogback.utils.ResponseResult;
import com.yecheng.leafblogback.utils.SystemConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.leafblogback.mapper.UserMapper;
import com.yecheng.leafblogback.bean.entity.User;
import com.yecheng.leafblogback.service.UserService;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2023-02-02 02:15:23
 */
@Service("userService")
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 登录
     *
     * @param user 用户
     * @return {@link ResponseResult}<{@link LoginVo}>
     */
    @Override
    public ResponseResult<LoginVo> login(LoginDto user) {
        if (Objects.isNull(user) || Objects.isNull(user.getUsername()) || Objects.isNull(user.getPassword())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAMS_ERROR);
        }
        String username = user.getUsername();
        String password = user.getPassword();
        User one = null;
        if (username.contains("@")) {
            one = lambdaQuery().eq(User::getEmail, username).one();
        } else {
            one = lambdaQuery().eq(User::getUsername, username).one();
        }
        if (Objects.isNull(one)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        String md5Pass = SecureUtil.md5(password + SystemConstant.MD5_PASSWORD_SALT);
        if (!one.getPassword().equals(md5Pass)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        if (Objects.isNull(one)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        HashMap<String, Object> map = new HashMap<>(6);
        map.put("userId", one.getId());
        map.put("userName", one.getUsername());
        map.put("email", one.getEmail());
        map.put("uuid", IdUtil.simpleUUID());
        map.put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24);
        String token = JWTUtil.createToken(map, SystemConstant.TOKEN_KEY.getBytes());

        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setUsername(one.getUsername());
        loginVo.setEmail(one.getEmail());
        loginVo.setPhonenumber(one.getPhonenumber());
        loginVo.setHeadimg(one.getHeadimg());
        loginVo.setPassword(one.getPassword());
        loginVo.setCreatetime(one.getCreatetime());
        stringRedisTemplate.opsForValue().set(SystemConstant.REDIS_TOKEN_KEY + one.getId(), JSONUtil.toJsonStr(loginVo), SystemConstant.REDIS_TOKEN_TTL, TimeUnit.MILLISECONDS);
        log.info("UserServiceImpl.login业务结束，结果为：{}", token);
        return ResponseResult.okResult(loginVo);
    }

    /**
     * 注销
     *
     * @return {@link ResponseResult}<{@link String}>
     */
    @Override
    public ResponseResult<String> logout() {
        Long userId = UserHolderContext.getUserId();
        stringRedisTemplate.delete(SystemConstant.REDIS_TOKEN_KEY + userId);
        log.info("UserServiceImpl.logout业务结束，结果为：{}", "退出成功！");
        return ResponseResult.okResult();
    }

    /**
     * 注册
     *
     * @param registerDto 注册dto
     * @return {@link ResponseResult}<{@link String}>
     */
    @Override
    public ResponseResult<String> register(RegisterDto registerDto) {
        String captcha = registerDto.getCaptcha();
        String email = registerDto.getEmail();
        String redisCaptcha = stringRedisTemplate.opsForValue().get(SystemConstant.REDIS_CAPTCHA_KEY + email);
        if (!StringUtils.hasText(redisCaptcha)) {
            return ResponseResult.errorResult(500, "验证码不存在或已过期！");
        }
        if (!redisCaptcha.equals(captcha)) {
            return ResponseResult.errorResult(500, "验证码错误！请输入最新验证码");
        }
        Long nameCount = lambdaQuery().eq(User::getUsername, registerDto.getUsername()).count();
        Long mailCount = lambdaQuery().eq(User::getEmail, registerDto.getEmail()).count();
        if (nameCount > 0 || mailCount > 0) {
            return ResponseResult.errorResult(500, "邮箱或用户名已被注册，请重新输入！");
        }
        String md5Pass = SecureUtil.md5(registerDto.getPassword() + SystemConstant.MD5_PASSWORD_SALT);
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(md5Pass);
        user.setEmail(registerDto.getEmail());
        user.setDeletestate(0);
        user.setUserstate(0);
        user.setCreateby(0);
        user.setUpdateby(0);
        boolean save = save(user);
        if (!save) {
            return ResponseResult.errorResult(500, "出现异常！请稍后重试！");
        }
        return ResponseResult.okResult(200, "注册成功！");
    }

    /**
     * 检查用户名是否存在
     *
     * @param userNameDto 用户名dto
     * @return {@link ResponseResult}<{@link String}>
     */
    @Override
    public ResponseResult<String> checkUserName(UserNameDto userNameDto) {
        // todo 此处还需要做防刷处理
        if (!Objects.isNull(UserHolderContext.getUserId())) {
            String username = userNameDto.getUsername();
            User user = getById(UserHolderContext.getUserId());
            if (username.equals(user.getUsername())) {
                return ResponseResult.okResult(200, "该用户名可用！");
            }
        }
        Long count = lambdaQuery().eq(User::getUsername, userNameDto.getUsername()).count();
        if (count > 0) {
            return ResponseResult.errorResult(500, "已存在该用户！");
        }
        return ResponseResult.okResult(200, "该用户名可用！");
    }

    /**
     * 检查电子邮件是否被注册过
     *
     * @param email 电子邮件
     * @return {@link ResponseResult}<{@link String}>
     */
    @Override
    public ResponseResult<String> checkEmail(String email) {
        // todo 此处还需要做防刷处理
        Long count = lambdaQuery().eq(User::getEmail, email).count();
        if (count > 0) {
            return ResponseResult.errorResult(500, "该邮箱已被注册！");
        }
        return ResponseResult.okResult(200, "该邮箱可用！");
    }

    /**
     * 忘记密码
     *
     * @param forgetPasswordDto 忘记密码dto
     * @return {@link ResponseResult}<{@link String}>
     */
    @Override
    public ResponseResult<String> forgetPassword(ForgetPasswordDto forgetPasswordDto) {
        String captcha = forgetPasswordDto.getCaptcha();
        String email = forgetPasswordDto.getEmail();
        String redisCaptcha = stringRedisTemplate.opsForValue().get(SystemConstant.REDIS_CAPTCHA_KEY + email);
        if (!StringUtils.hasText(redisCaptcha)) {
            return ResponseResult.errorResult(500, "验证码不存在或已过期！");
        }
        if (!redisCaptcha.equals(captcha)) {
            return ResponseResult.errorResult(500, "验证码错误！请输入最新验证码");
        }
        User user = lambdaQuery().eq(User::getEmail, forgetPasswordDto.getEmail()).one();
        if (Objects.isNull(user)) {
            return ResponseResult.errorResult(500, "不存在该邮箱的账号！");
        }
        String md5Pass = SecureUtil.md5(forgetPasswordDto.getPassword() + SystemConstant.MD5_PASSWORD_SALT);
        user.setPassword(md5Pass);
        boolean b = updateById(user);
        if (!b) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ERROR);
        }
        return ResponseResult.okResult(200, "密码重置成功！");
    }

    /**
     * 更新姓名和电话
     *
     * @param updateNameAndPhoneDto 更新姓名和电话dto
     * @return {@link ResponseResult}<{@link String}>
     */
    @Override
    public ResponseResult<LoginVo> updateNameAndPhone(UpdateNameAndPhoneDto updateNameAndPhoneDto) {
        Long userId = UserHolderContext.getUserId();
        if (Objects.isNull(userId)) {
            return ResponseResult.errorResult(500, "登录过期！或还未登录！");
        }
        User user = getById(userId);
        Long count = lambdaQuery().eq(User::getUsername, updateNameAndPhoneDto.getUsername()).count();
        if (!user.getUsername().equals(updateNameAndPhoneDto.getUsername())) {
            if (count > 0) {
                return ResponseResult.errorResult(500, "该用户名已被使用！请重新输入！");
            }
        }
        if (!updateNameAndPhoneDto.getUsername().equals(user.getUsername())) {
            user.setUsername(updateNameAndPhoneDto.getUsername());
        }
        user.setPhonenumber(updateNameAndPhoneDto.getPhonenumber());
        if (!Objects.isNull(updateNameAndPhoneDto.getHeadimg()) || updateNameAndPhoneDto.getHeadimg() != "") {
            if (!updateNameAndPhoneDto.getHeadimg().equals(user.getHeadimg())) {
                user.setHeadimg(updateNameAndPhoneDto.getHeadimg());
            }
        }
        boolean b = updateById(user);
        if (!b) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ERROR);
        }
        User user1 = getById(user.getId());
        LoginVo loginVo = new LoginVo();
        loginVo.setUsername(user1.getUsername());
        loginVo.setEmail(user1.getEmail());
        loginVo.setPhonenumber(user1.getPhonenumber());
        loginVo.setHeadimg(user1.getHeadimg());
        loginVo.setPassword(user1.getPassword());
        loginVo.setCreatetime(user1.getCreatetime());

        return ResponseResult.okResult(loginVo);
    }
}
