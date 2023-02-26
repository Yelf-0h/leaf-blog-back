package com.yecheng.leafblogback.controller;

import com.yecheng.leafblogback.bean.dto.EmailDto;
import com.yecheng.leafblogback.service.SendEmailService;
import com.yecheng.leafblogback.utils.ResponseResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 发送电子邮件味精控制器
 *
 * @author Yelf
 * @create 2023-02-25-17:39
 * @date 2023/02/25
 */
@RestController
public class SendEmailMsgController {

    @Resource
    private SendEmailService sendEmailService;

    @GetMapping("sendEmail")
    public ResponseResult<Object> sendEmailCode(@Validated EmailDto emailDto){
        return sendEmailService.sendEmailCode(emailDto.getEmail());
    }
}
