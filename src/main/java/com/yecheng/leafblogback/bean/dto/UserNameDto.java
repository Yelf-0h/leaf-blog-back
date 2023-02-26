package com.yecheng.leafblogback.bean.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author Yelf
 * @create 2023-02-26-0:48
 */
@Data
public class UserNameDto {
    @NotBlank(message = "用户名不能为空！")
    private String username;
}
