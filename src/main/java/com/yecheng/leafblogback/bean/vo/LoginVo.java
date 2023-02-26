package com.yecheng.leafblogback.bean.vo;

import lombok.Data;

/**
 * @author Yelf
 * @create 2023-02-17-19:47
 */
@Data
public class LoginVo {
    private String token;
    private String username;
    private String email;
    private String phonenumber;
    private String headimg;
    private String password;
    private String createtime;

}
