package com.yecheng.leafblogback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.leafblogback.entity.Review;
import com.yecheng.leafblogback.utils.ResponseResult;

/**
 * (Review)表服务接口
 *
 * @author makejava
 * @since 2023-02-02 02:15:23
 */
public interface ReviewService extends IService<Review> {

    /**
     * 添加游览量
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    ResponseResult addReview(Long id);
}
