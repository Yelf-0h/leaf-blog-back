package com.yecheng.leafblogback.bean.dto;

import lombok.Data;

/**
 * @author Yelf
 * @create 2023-02-08-21:09
 */
@Data
public class ArticleListByCategoryDto extends PageDto{
    private Long categoryId;

}
