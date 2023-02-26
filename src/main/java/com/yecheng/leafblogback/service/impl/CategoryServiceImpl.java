package com.yecheng.leafblogback.service.impl;

import com.yecheng.leafblogback.utils.ResponseResult;
import com.yecheng.leafblogback.bean.vo.CategoryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.leafblogback.mapper.CategoryMapper;
import com.yecheng.leafblogback.bean.entity.Category;
import com.yecheng.leafblogback.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

/**
 * (Category)表服务实现类
 *
 * @author makejava
 * @since 2023-02-05 02:23:16
 */
@Service("categoryService")
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    /**
     * 类别列表
     *
     * @return {@link ResponseResult}<{@link CategoryVo}>
     */
    @Override
    public ResponseResult<CategoryVo> categoryList() {

        List<CategoryVo> categoryVos = new ArrayList<>();
        list().forEach(category -> {
            CategoryVo categoryVo = new CategoryVo();
            categoryVo.setId(category.getId());
            categoryVo.setCategoryName(category.getCategoryName());
            categoryVos.add(categoryVo);
        });
        log.info("CategoryServiceImpl.categoryList业务结束，结果为：{}",categoryVos);
        return ResponseResult.okResult(categoryVos);
    }
}
