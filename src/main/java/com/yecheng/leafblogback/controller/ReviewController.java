package com.yecheng.leafblogback.controller;

import com.yecheng.leafblogback.service.ReviewService;
import com.yecheng.leafblogback.utils.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Yelf
 * @create 2023-02-05-19:43
 */
@RestController
@RequestMapping("review")
public class ReviewController {

    @Resource
    private ReviewService reviewService;

    @GetMapping("add")
    public ResponseResult addReview(Long id){
        System.out.println("变更55555555555555555555555555555555555");
        return reviewService.addReview(id);
    }
}
