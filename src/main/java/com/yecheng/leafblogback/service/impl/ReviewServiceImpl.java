package com.yecheng.leafblogback.service.impl;

import com.yecheng.leafblogback.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.leafblogback.mapper.ReviewMapper;
import com.yecheng.leafblogback.bean.entity.Review;
import com.yecheng.leafblogback.service.ReviewService;

import static com.yecheng.leafblogback.utils.AppHttpCodeEnum.REVIEW_ERROR;

/**
 * (Review)表服务实现类
 *
 * @author makejava
 * @since 2023-02-02 02:15:23
 */
@Service("reviewService")
@Slf4j
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements ReviewService {

    /**
     * 添加文章游览量
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult addReview(Long id) {
        Review review = lambdaQuery().eq(Review::getArticleid, id).one();
        review.setReview(review.getReview()+1);
        boolean b = updateById(review);
        if (!b){
            return ResponseResult.errorResult(REVIEW_ERROR);
        }
        log.info("ReviewServiceImpl.addReview业务结束，结果为：{}","游览量加 1");
        return ResponseResult.okResult();
    }
}
