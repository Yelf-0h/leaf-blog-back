package com.yecheng.leafblogback.bean.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Logininfo)表实体类
 *
 * @author makejava
 * @since 2023-02-02 02:15:23
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ly_logininfo")
public class Logininfo  {
    @TableId(type = IdType.AUTO)
    /**
    id主键
    */    
    private Long id;

    /**
    用户id包括管理员id
    */
    private Long userid;
    /**
    最后一次登录的ip地址
    */
    private String loginip;
    /**
    最后一次登录的时间
    */
    private Date logintime;
    /**
    登录的次数
    */
    private Long logincount;
    /**
    是否为管理员（0为否，1为是）
    */
    private Integer isadmin;



}
