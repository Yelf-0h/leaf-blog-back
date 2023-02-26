package com.yecheng.leafblogback.bean.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

/**
 * @author Yelf
 * @create 2023-02-25-22:16
 */
@Data
public class RegisterDto {
    @NotBlank(message = "用户名不能为空！")
    @Length(max = 20,min = 2,message = "用户名参数异常")
    private String username;
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
