package com.yecheng.leafblogback.controller;

import com.yecheng.leafblogback.service.CategoryService;
import com.yecheng.leafblogback.utils.ResponseResult;
import com.yecheng.leafblogback.bean.vo.CategoryVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Yelf
 * @create 2023-02-05-3:31
 */
@RestController
@RequestMapping("category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;


    /**
     * 类别列表
     *
     * @return {@link ResponseResult}<{@link CategoryVo}>
     */
    @GetMapping("list")
    public ResponseResult<CategoryVo> categoryList(){
        return categoryService.categoryList();
    }
}
