package com.yecheng.leafblogback.bean.vo;

import lombok.Data;

import java.util.Date;

/**
 * 文章信息返回的vo
 * @author Yelf
 * @create 2023-02-02-19:50
 */
@Data
public class ArticleVo {

    /**
     * 文章id
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
     文章的类别名
     */
    private String category;
    /**
     文章缩略图地址
     */
    private String articleimgurl;
    /**
     文章内容
     */
    private String articlecontent;
    /**
     文章作者的名字
     */
    private String createbyName;
    /**
     文章创建时间
     */

    private String createtime;

    /**
     * 游览量
     */
    private Long review;

    /**
     * 评论数
     */
    private Long commentSum;

    private Integer istop;
}
