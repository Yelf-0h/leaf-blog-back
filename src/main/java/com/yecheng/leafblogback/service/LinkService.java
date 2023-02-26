package com.yecheng.leafblogback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.leafblogback.bean.entity.Link;
import com.yecheng.leafblogback.utils.ResponseResult;

import java.util.List;

/**
 * (Link)表服务接口
 *
 * @author makejava
 * @since 2023-02-27 03:23:15
 */
public interface LinkService extends IService<Link> {

    /**
     * 链接列表
     *
     * @return {@link ResponseResult}<{@link Link}>
     */
    ResponseResult<List<Link>> linkList();
}
