package com.yecheng.leafblogback.entity;

import java.util.Date;

import java.io.Serializable;

import com.yecheng.leafblogback.utils.ResponseResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2023-02-02 02:15:23
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ly_user")
public class User  {
    @TableId
    /**
    主键id
    */    
    private Long id;

    /**
    用户登录名
    */
    private String username;
    /**
    密码
    */
    private String password;
    /**
    邮箱
    */
    private String email;
    /**
    手机号
    */
    private Long phonenumber;
    /**
    是否删除(0正常，1删除)
    */
    private Integer deletestate;
    /**
    用户状态(0启用，1禁用)
    */
    private Integer userstate;
    /**
    创建该账号的用户id(0自身注册)
    */
    private Object createby;
    /**
    创建账号的时间
    */
    private Date createtime;
    /**
    更新用户信息的用户id(0自身修改)
    */
    private Object updateby;
    /**
    更新账号信息的时间
    */
    private Date updatetime;




}
