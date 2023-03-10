package com.yecheng.leafblogback.bean.entity;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Review)表实体类
 *
 * @author makejava
 * @since 2023-02-02 02:15:23
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ly_review")
public class Review  {
    @TableId(type = IdType.AUTO)
    /**
    主键id
    */    
    private Long id;

    /**
    文章id
    */
    private Long articleid;
    /**
    游览量
    */
    private Long review;



}
