package com.yecheng.leafblogback.bean.dto;

import lombok.Data;

/**
 * 分页参数
 * @author Yelf
 * @create 2023-02-02-20:36
 */
@Data
public class PageDto {
    private Integer pageNum = 1;
    private Integer pageSize = 6;
}
