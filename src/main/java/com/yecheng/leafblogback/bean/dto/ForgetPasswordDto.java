package com.yecheng.leafblogback.bean.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author Yelf
 * @create 2023-02-26-4:06
 */
@Data
public class ForgetPasswordDto {
    @NotBlank(message = "验证码不能为空！")
    @Length(max = 6,min = 6,message = "验证码参数异常")
    private String captcha;
    @Email(message = "邮箱格式不正确！")
    @NotBlank(message = "邮箱不能为空！")
    @Length(max = 30,min = 6,message = "邮箱参数异常")
    private String email;
    @NotBlank(message = "密码不能为空！")
    @Length(max = 30,min = 6,message = "密码参数异常")
    private String password;
}
