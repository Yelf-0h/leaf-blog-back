package com.yecheng.leafblogback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.leafblogback.bean.dto.*;
import com.yecheng.leafblogback.bean.entity.User;
import com.yecheng.leafblogback.bean.vo.LoginVo;
import com.yecheng.leafblogback.utils.ResponseResult;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2023-02-02 02:15:23
 */
public interface UserService extends IService<User> {

    /**
     * 登录
     *
     * @param user 用户
     * @return {@link ResponseResult}<{@link LoginVo}>
     */
    ResponseResult<LoginVo> login(LoginDto user);

    /**
     * 注销
     *
     * @return {@link ResponseResult}<{@link String}>
     */
    ResponseResult<String> logout();

    /**
     * 注册
     *
     * @param registerDto 注册dto
     * @return {@link ResponseResult}<{@link String}>
     */
    ResponseResult<String> register(RegisterDto registerDto);

    /**
     * 检查用户名是否存在
     *
     * @param userNameDto 用户名dto
     * @return {@link ResponseResult}<{@link String}>
     */
    ResponseResult<String> checkUserName(UserNameDto userNameDto);

    /**
     * 检查电子邮件是否被注册过
     *
     * @param email 电子邮件
     * @return {@link ResponseResult}<{@link String}>
     */
    ResponseResult<String> checkEmail(String email);

    /**
     * 忘记密码
     *
     * @param forgetPasswordDto 忘记密码dto
     * @return {@link ResponseResult}<{@link String}>
     */
    ResponseResult<String> forgetPassword(ForgetPasswordDto forgetPasswordDto);

    /**
     * 更新姓名和电话
     *
     * @param updateNameAndPhoneDto 更新姓名和电话dto
     * @return {@link ResponseResult}<{@link String}>
     */
    ResponseResult<LoginVo> updateNameAndPhone(UpdateNameAndPhoneDto updateNameAndPhoneDto);
}
