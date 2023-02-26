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
 * (Link)表实体类
 *
 * @author makejava
 * @since 2023-02-27 03:23:12
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ly_link")
public class Link  {
    @TableId(type = IdType.AUTO)
    /**
    主键id
    */    
    private Long id;

    /**
    友链名
    */
    private String linkName;
    /**
    链接地址
    */
    private String linkUrl;
    /**
    创建时间
    */
    private String createTime;
    /**
    优先级，默认0
    */
    private Integer top;



}
