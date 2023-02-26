package com.yecheng.leafblogback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.leafblogback.bean.dto.PageDto;
import com.yecheng.leafblogback.bean.entity.Comment;
import com.yecheng.leafblogback.bean.vo.CommentTreeVo;
import com.yecheng.leafblogback.utils.ResponseResult;

import java.util.List;

/**
 * (Comment)表服务接口
 *
 * @author makejava
 * @since 2023-02-02 02:15:23
 */
public interface CommentService extends IService<Comment> {

    /**
     * 获取留言页面
     *
     * @param pageDto 页面dto
     * @return {@link ResponseResult}<{@link List}<{@link CommentTreeVo}>>
     */
    ResponseResult<List<CommentTreeVo>> getMessagePage(PageDto pageDto);

    /**
     * 保存信息
     *
     * @param content 内容
     * @return {@link ResponseResult}<{@link Object}>
     */
    ResponseResult<Object> saveMessage(String content);

    /**
     * 保存信息
     *
     * @param content 内容
     * @param paterid paterid
     * @return {@link ResponseResult}<{@link Object}>
     */
    ResponseResult<Object> saveMessage(String content,Long paterid);
}
