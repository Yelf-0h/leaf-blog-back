package com.yecheng.leafblogback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.leafblogback.Dto.PageDto;
import com.yecheng.leafblogback.entity.Articleinfo;
import com.yecheng.leafblogback.utils.ResponseResult;
import com.yecheng.leafblogback.vo.ArticleVo;
import com.yecheng.leafblogback.vo.HotArticleVo;
import com.yecheng.leafblogback.vo.TimeArticleVo;

import java.util.List;

/**
 * (Articleinfo)表服务接口
 *
 * @author makejava
 * @since 2023-02-02 02:15:23
 */
public interface ArticleinfoService extends IService<Articleinfo> {

    /**
     *
     * 文章页面的集合
     *
     * @param pageDto 页面dto
     * @return {@link ResponseResult}<{@link List}<{@link ArticleVo}>>
     */
    ResponseResult<List<ArticleVo>> articlePage(PageDto pageDto);

    /**
     * 热门文章
     *
     * @return {@link ResponseResult}<{@link List}<{@link HotArticleVo}>>
     */
    ResponseResult<List<HotArticleVo>> hotArticle();

    /**
     * 时间线文章
     * 只返回前五篇 按时间戳降序 返回最新的五篇
     * @return {@link ResponseResult}<{@link List}<{@link TimeArticleVo}>>
     */
    ResponseResult<List<TimeArticleVo>> timeArticles();

    /**
     * 通过文章id查询文章详情
     *
     * @param id id
     * @return {@link ResponseResult}<{@link ArticleVo}>
     */
    ResponseResult<ArticleVo> articleDetailById(Long id);
}


