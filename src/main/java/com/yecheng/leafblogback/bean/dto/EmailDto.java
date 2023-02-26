package com.yecheng.leafblogback.bean.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Yelf
 * @create 2023-02-25-18:15
 */
@Data
public class EmailDto {
    @Email(message = "邮箱格式出错！")
    @NotBlank(message = "邮箱不能为空！")
    private String email;

}
