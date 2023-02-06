package com.yecheng.leafblogback.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Category)表实体类
 *
 * @author makejava
 * @since 2023-02-05 02:23:16
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ly_category")
public class Category  {
    @TableId
    /**
    主键id
    */    
    private Long id;

    /**
    分类名
    */
    private String categoryName;
    /**
    创建人id
    */
    private Long createby;
    /**
    创建时间
    */
    private Date createtime;
    /**
    更新人id
    */
    private Long updateby;
    /**
    更新时间
    */
    private Date updatetime;



}
