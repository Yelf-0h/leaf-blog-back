package com.yecheng.leafblogback.bean.vo;

import lombok.Data;

import javax.jws.HandlerChain;
import java.util.Date;
import java.util.List;

/**
 * @author Yelf
 * @create 2023-02-13-0:40
 */
@Data

public class CommentTreeVo {

    private Long id;

    /**
     文章id,文章id为0则为留言
     */
    private Long articleid;
    /**
     该评论的用户id
     */
    private Long userid;

    private String headimg;

    private String username;

    private String tousername;
    /**
     评论所属id级别（0是父级）
     */
    private Object paterid;
    /**
     评论时间
     */
    private String createtime;

    private String content;

    private List<CommentTreeVo> replyList;

    private final boolean isReply = false;
}
