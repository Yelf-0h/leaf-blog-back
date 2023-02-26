package com.yecheng.leafblogback.bean.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Yelf
 * @create 2023-02-26-19:30
 */
@Data
public class UpdateNameAndPhoneDto {
    @NotBlank(message = "用户名不能为空！")
    @Length(max = 20,min = 2,message = "长度大小应该在2-20个字符！")
    private String username;
    @NotBlank
    @Pattern(regexp = "0?(13|14|15|18|17|19)[0-9]{9}",message = "手机号格式错误！")
    private String phonenumber;

    private String headimg;
}
