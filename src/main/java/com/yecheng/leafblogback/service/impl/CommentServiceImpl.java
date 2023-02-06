package com.yecheng.leafblogback.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.leafblogback.mapper.CommentMapper;
import com.yecheng.leafblogback.entity.Comment;
import com.yecheng.leafblogback.service.CommentService;

/**
 * (Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-02-02 02:15:23
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
