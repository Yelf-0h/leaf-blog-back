package com.yecheng.leafblogback.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Admin)表实体类
 *
 * @author makejava
 * @since 2023-02-02 02:14:20
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ly_admin")
public class Admin  {
    @TableId
    /**
    id主键
    */    
    private Long id;

    /**
    管理员用户名
    */
    private String username;
    /**
    管理员密码
    */
    private String password;
    /**
    管理员邮箱
    */
    private String email;
    /**
    管理员手机号
    */
    private Long phonenumber;
    /**
    是否删除（0正常，1已删除）
    */
    private Integer deletestate;
    /**
    创建者id
    */
    private Long createby;
    /**
    创建时间
    */
    private Date createtime;
    /**
    更新者id
    */
    private Long updateby;
    /**
    更新时间
    */
    private Date updatetime;
    /**
    管理员账号状态（0正常，1停用）
    */
    private Integer adminstate;



}
