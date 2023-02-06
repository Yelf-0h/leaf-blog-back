package com.yecheng.leafblogback.vo;

import lombok.Data;

/**
 * @author Yelf
 * @create 2023-02-04-2:28
 */
@Data
public class HotArticleVo {
    /**
     * 文章id
     *
     */
    private Long id;

    /**
     * 文章标题
     */
    private String articletitle;

    /**
     * 游览量
     */
    private Long review;
}
