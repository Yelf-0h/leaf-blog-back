package com.yecheng.leafblogback.controller;

import com.yecheng.leafblogback.bean.dto.ArticleListByCategoryDto;
import com.yecheng.leafblogback.bean.dto.PageDto;
import com.yecheng.leafblogback.service.ArticleinfoService;
import com.yecheng.leafblogback.utils.ResponseResult;
import com.yecheng.leafblogback.bean.vo.ArticleVo;
import com.yecheng.leafblogback.bean.vo.HotArticleVo;
import com.yecheng.leafblogback.bean.vo.TimeArticleVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文章控制器
 *
 * @author Yelf
 * @create 2023-02-02-19:29
 * @date 2023/02/02
 */
@RestController
@RequestMapping("article")
public class ArticleController {
    @Resource
    private ArticleinfoService articleinfoService;

    /**
     *
     * 文章页面的集合
     *
     * @param pageDto 页面dto
     * @return {@link ResponseResult}<{@link List}<{@link ArticleVo}>>
     */
    @GetMapping("list")
    public ResponseResult<List<ArticleVo>> articleList(PageDto pageDto){
        return articleinfoService.articlePage(pageDto);
    }

    /**
     * 热门文章
     * 只返回前5篇
     * @return {@link ResponseResult}<{@link List}<{@link HotArticleVo}>>
     */
    @GetMapping("hot")
    public ResponseResult<List<HotArticleVo>> hotArticles(){
        return articleinfoService.hotArticle();
    }

    /**
     * 时间线文章
     * 只返回前五篇 按时间戳降序 返回最新的五篇
     * @return {@link ResponseResult}<{@link List}<{@link TimeArticleVo}>>
     */
    @GetMapping("timeArticle")
    public ResponseResult<List<TimeArticleVo>> timeArticles(){
        return articleinfoService.timeArticles();
    }
    /**
     * 通过文章id查询文章详情
     *
     * @param id id
     * @return {@link ResponseResult}<{@link ArticleVo}>
     */
    @GetMapping("detail/{id}")
    public ResponseResult<ArticleVo> getArticleDetail(@PathVariable("id") Long id){
        return articleinfoService.articleDetailById(id);
    }
    /**
     * 按类别查找文章列表
     *
     * @param byCategoryDto 按类别dto
     * @return {@link ResponseResult}<{@link List}<{@link ArticleVo}>>
     */
    @PostMapping("category")
    public ResponseResult<List<ArticleVo>> getArticleListByCategory(@RequestBody ArticleListByCategoryDto byCategoryDto){
        return articleinfoService.articleListByCategory(byCategoryDto);
    }


}
