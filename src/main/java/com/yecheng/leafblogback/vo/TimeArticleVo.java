package com.yecheng.leafblogback.vo;

import lombok.Data;

import java.util.Date;

/**
 * 文章时间线
 * @author Yelf
 * @create 2023-02-05-1:46
 */
@Data
public class TimeArticleVo {
    /**
     * 文章id
     */
    private Long id;
    /**
     文章标题
     */
    private String articletitle;
    /**
     文章作者的名字
     */
    private String createbyName;
    /**
     文章创建时间
     */
    private String createtime;

}
