package com.yecheng.leafblogback.service.impl;

import com.yecheng.leafblogback.utils.ResponseResult;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.leafblogback.mapper.LinkMapper;
import com.yecheng.leafblogback.bean.entity.Link;
import com.yecheng.leafblogback.service.LinkService;

import java.util.List;

/**
 * (Link)表服务实现类
 *
 * @author makejava
 * @since 2023-02-27 03:23:15
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    /**
     * 链接列表
     *
     * @return {@link ResponseResult}<{@link List}<{@link Link}>>
     */
    @Override
    public ResponseResult<List<Link>> linkList() {
        List<Link> list = list();
        return ResponseResult.okResult(list);
    }
}
