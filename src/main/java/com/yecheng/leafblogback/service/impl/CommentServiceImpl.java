package com.yecheng.leafblogback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yecheng.leafblogback.bean.dto.PageDto;
import com.yecheng.leafblogback.bean.entity.User;
import com.yecheng.leafblogback.bean.vo.CommentTreeVo;
import com.yecheng.leafblogback.interceptor.UserHolderContext;
import com.yecheng.leafblogback.service.UserService;
import com.yecheng.leafblogback.utils.AppHttpCodeEnum;
import com.yecheng.leafblogback.utils.ResponseResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.leafblogback.mapper.CommentMapper;
import com.yecheng.leafblogback.bean.entity.Comment;
import com.yecheng.leafblogback.service.CommentService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * (Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-02-02 02:15:23
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private UserService userService;
    /**
     * 获取留言页面
     *
     * @param pageDto 页面dto
     * @return {@link ResponseResult}<{@link List}<{@link CommentTreeVo}>>
     */
    @Override
    public ResponseResult<List<CommentTreeVo>> getMessagePage(PageDto pageDto) {
        if (Objects.isNull(pageDto) || pageDto.getPageSize() < 1 || pageDto.getPageNum() < 1) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ERROR);
        }
        Page<Comment> commentPage = new Page<>(pageDto.getPageNum(), pageDto.getPageSize());
        LambdaQueryWrapper<Comment> commentQw = new LambdaQueryWrapper<>();
        commentQw.eq(Comment::getArticleid, 0);
        commentQw.eq(Comment::getPaterid, 0);
        commentQw.orderByDesc(Comment::getCreatetime);
        page(commentPage, commentQw);
        List<Comment> mainComment = commentPage.getRecords();
        List<CommentTreeVo> commentTreeVos = new ArrayList<>();
        for (Comment comment : mainComment) {
            CommentTreeVo treeVo = new CommentTreeVo();
            BeanUtils.copyProperties(comment, treeVo);
            // 设置0级的评论用户名
            Long userid = comment.getUserid();
            User user = userService.getById(userid);
            treeVo.setUsername(user.getUsername());
            treeVo.setHeadimg(user.getHeadimg());
            commentTreeVos.add(treeVo);
        }
        List<CommentTreeVo> commentTreeVoList = getCollect(commentTreeVos);
        for (CommentTreeVo commentTreeVo : commentTreeVoList) {
            if (commentTreeVo.getReplyList() != null || !commentTreeVo.getReplyList().isEmpty()){
                List<CommentTreeVo> list = addTree(commentTreeVo.getReplyList());
                commentTreeVo.setReplyList(list);
            }
        }

        return ResponseResult.okResult(commentTreeVoList, commentPage.getTotal());
    }

    /**
     * 保存信息
     *
     * @param content 内容
     * @return {@link ResponseResult}<{@link Object}>
     */
    @Override
    public ResponseResult<Object> saveMessage(String content) {
        Comment comment = new Comment();
        comment.setArticleid(0L);
        comment.setUserid(UserHolderContext.getUserId());
        comment.setPaterid(0);
        comment.setContent(content);
        boolean save = save(comment);
        if (!save){
            return ResponseResult.errorResult(AppHttpCodeEnum.ERROR);
        }
        return ResponseResult.okResult();
    }

    /**
     * 保存信息
     *
     * @param content 内容
     * @param paterid paterid
     * @return {@link ResponseResult}<{@link Object}>
     */
    @Override
    public ResponseResult<Object> saveMessage(String content, Long paterid) {
        Comment comment = new Comment();
        comment.setArticleid(0L);
        comment.setUserid(UserHolderContext.getUserId());
        comment.setPaterid(paterid);
        comment.setContent(content);
        boolean save = save(comment);
        if (!save){
            return ResponseResult.errorResult(AppHttpCodeEnum.ERROR);
        }
        return ResponseResult.okResult();
    }

    /**
     * 将子树结构拆成2级子树
     *
     * @param replyList 回复列表
     * @return {@link List}<{@link CommentTreeVo}>
     */
    private List<CommentTreeVo> addTree(List<CommentTreeVo> replyList){
        List<CommentTreeVo> commentTreeVos = new ArrayList<>();
        List<CommentTreeVo> result =new ArrayList<>();
        for (CommentTreeVo commentTreeVo : replyList) {
            if (commentTreeVo.getReplyList() != null || !commentTreeVo.getReplyList().isEmpty()){
               commentTreeVos = commentTreeVo.getReplyList();
               commentTreeVo.setReplyList(null);
               result.add(commentTreeVo);
               List<CommentTreeVo> treeVos = addTree(commentTreeVos);
               result.addAll(treeVos);

            }else {
                result.add(commentTreeVo);
            }
        }
        return result;
    }


    /**
     * 获取评论子树结构
     *
     * @param commentTreeVos 评论树vos
     * @return {@link List}<{@link CommentTreeVo}>
     */
    @NotNull
    private List<CommentTreeVo> getCollect(List<CommentTreeVo> commentTreeVos) {

        for (CommentTreeVo commentTreeVo : commentTreeVos) {
            // 查询父id评论的子评论
            Long id = commentTreeVo.getId();
            LambdaQueryWrapper<Comment> commentQw2 = new LambdaQueryWrapper<>();
            commentQw2.eq(Comment::getPaterid, id);
            commentQw2.orderByDesc(Comment::getCreatetime);
            List<Comment> comments = list(commentQw2);
            if (comments != null) {
                List<CommentTreeVo> commentTreeVos1 = new ArrayList<>();
                for (Comment comment : comments) {
                    CommentTreeVo treeVo = new CommentTreeVo();
                    BeanUtils.copyProperties(comment, treeVo);
                    // 设置当前评论者的用户名以及对谁回复的用户名
                    Long userid = comment.getUserid();
                    User user = userService.getById(userid);
                    Comment paterComment = getById(id);
                    User paterUser = userService.getById(paterComment.getUserid());
                    treeVo.setUsername(user.getUsername());
                    treeVo.setHeadimg(user.getHeadimg());
                    treeVo.setTousername(paterUser.getUsername());
                    // 设置到子树
                    commentTreeVos1.add(treeVo);
                }
                commentTreeVo.setReplyList(commentTreeVos1);

                getCollect(commentTreeVos1);
            }
        }
        return commentTreeVos;
    }

}
