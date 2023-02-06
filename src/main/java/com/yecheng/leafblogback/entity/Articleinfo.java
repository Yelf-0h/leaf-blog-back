package com.yecheng.leafblogback.entity;

import java.util.Date;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * (Articleinfo)表实体类
 *
 * @author makejava
 * @since 2023-02-02 02:15:23
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ly_articleinfo")
public class Articleinfo  {
    @TableId
    /**
    主键id
    */    
    private Long id;

    /**
    文章标题
    */
    private String articletitle;
    /**
    文章描述
    */
    private String articleaccount;
    /**
    文章的类别id
    */
    private Long categoryid;
    /**
    文章缩略图地址
    */
    private String articleimgurl;
    /**
    文章内容
    */
    private String articlecontent;
    /**
    文章作者的id
    */
    private Long createby;
    /**
    文章创建时间
    */

    private String createtime;
    /**
    更新者的id
    */
    private Long updateby;
    /**
    更新的时间
    */

    private String updatetime;
    /**
    文章状态（0正常，1停用）
    */
    private Integer articlestate;
    /**
    推荐权重
    */
    private Integer istop;



}
