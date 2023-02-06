package com.yecheng.leafblogback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.leafblogback.entity.Category;
import com.yecheng.leafblogback.utils.ResponseResult;
import com.yecheng.leafblogback.vo.CategoryVo;

/**
 * (Category)表服务接口
 *
 * @author makejava
 * @since 2023-02-05 02:23:16
 */
public interface CategoryService extends IService<Category> {

    /**
     * 类别列表
     *
     * @return {@link ResponseResult}<{@link CategoryVo}>
     */
    ResponseResult<CategoryVo> categoryList();
}
