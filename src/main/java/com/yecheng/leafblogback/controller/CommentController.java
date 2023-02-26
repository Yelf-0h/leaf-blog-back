package com.yecheng.leafblogback.controller;

import com.yecheng.leafblogback.bean.dto.MessageDto;
import com.yecheng.leafblogback.bean.dto.PageDto;
import com.yecheng.leafblogback.bean.vo.CommentTreeVo;
import com.yecheng.leafblogback.exception.UnAuthenticationException;
import com.yecheng.leafblogback.interceptor.UserHolderContext;
import com.yecheng.leafblogback.service.CommentService;
import com.yecheng.leafblogback.utils.AppHttpCodeEnum;
import com.yecheng.leafblogback.utils.ResponseResult;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author Yelf
 * @create 2023-02-13-0:38
 */
@RestController
@RequestMapping("comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    @PostMapping("message")
    public ResponseResult<List<CommentTreeVo>> getMessagePage(@RequestBody PageDto pageDto) {
        return commentService.getMessagePage(pageDto);
    }

    @GetMapping("saveMessage")
    public ResponseResult<Object> saveMessage(MessageDto messageDto) {
        if (!StringUtils.hasText(messageDto.getContent())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ERROR);
        }
        if (Objects.isNull(UserHolderContext.getUserId())) {
            throw new UnAuthenticationException();
        }
        if (Objects.isNull(messageDto.getPaterid())) {
            return commentService.saveMessage(messageDto.getContent());
        }else {
            return commentService.saveMessage(messageDto.getContent(),messageDto.getPaterid());
        }
    }
}
