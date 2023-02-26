package com.yecheng.leafblogback.service;

import com.yecheng.leafblogback.bean.dto.EmailDto;
import com.yecheng.leafblogback.utils.ResponseResult;

/**
 * @author Yelf
 * @create 2023-02-25-18:25
 */
public interface SendEmailService {


    /**
     * 发送电子邮件验证码
     *
     * @param email 电子邮件
     * @return {@link ResponseResult}<{@link Object}>
     */
    ResponseResult<Object> sendEmailCode(String email);
}
