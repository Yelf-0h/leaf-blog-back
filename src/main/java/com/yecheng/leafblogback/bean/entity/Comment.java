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
 * (Comment)表实体类
 *
 * @author makejava
 * @since 2023-02-02 02:15:23
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ly_comment")
public class Comment  {
    @TableId(type = IdType.AUTO)
    /**
    主键id
    */    
    private Long id;

    /**
    文章id,文章id为0则为留言
    */
    private Long articleid;
    /**
    该评论的用户id
    */
    private Long userid;
    /**
    评论所属id级别（0是父级）
    */
    private Object paterid;
    /**
    评论时间
    */
    private String createtime;

    private String content;



}
