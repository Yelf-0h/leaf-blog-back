package com.yecheng.leafblogback.controller;

import com.yecheng.leafblogback.bean.entity.Link;
import com.yecheng.leafblogback.service.LinkService;
import com.yecheng.leafblogback.utils.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yelf
 * @create 2023-02-27-3:25
 */
@RestController
@RequestMapping("link")
public class LinkController {

    @Resource
    private LinkService linkService;

    @GetMapping("linkList")
    public ResponseResult<List<Link>> linkList(){
        return linkService.linkList();
    }
}
