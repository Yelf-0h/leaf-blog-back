package com.yecheng.leafblogback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yecheng.leafblogback.bean.dto.ArticleListByCategoryDto;
import com.yecheng.leafblogback.bean.dto.PageDto;
import com.yecheng.leafblogback.bean.entity.*;
import com.yecheng.leafblogback.service.*;
import com.yecheng.leafblogback.utils.AppHttpCodeEnum;
import com.yecheng.leafblogback.utils.ResponseResult;
import com.yecheng.leafblogback.bean.vo.ArticleVo;
import com.yecheng.leafblogback.bean.vo.HotArticleVo;
import com.yecheng.leafblogback.bean.vo.TimeArticleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.leafblogback.mapper.ArticleinfoMapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (Articleinfo)表服务实现类
 *
 * @author makejava
 * @since 2023-02-02 02:15:23
 */
@Service("articleinfoService")
@Slf4j
public class ArticleinfoServiceImpl extends ServiceImpl<ArticleinfoMapper, Articleinfo> implements ArticleinfoService {

    /**
     * 最大页面大小
     * 避免无限制被一次性刷卡
     */
    private static final Integer maxPageSize = 18;

    @Resource
    private AdminService adminService;

    @Resource
    private ReviewService reviewService;

    @Resource
    private CommentService commentService;

    @Resource
    private CategoryService categoryService;


    /**
     * 获取文章页面列表
     *
     * @param pageDto 页面dto
     * @return {@link ResponseResult}<{@link List}<{@link ArticleVo}>>
     */
    @Override
    public ResponseResult<List<ArticleVo>> articlePage(PageDto pageDto) {
        // 限制最大页面大小为18，如果超过18的一律按18来返回
        if (pageDto.getPageSize() > maxPageSize) {
            log.info("ArticleinfoServiceImpl.articlePage业务中，出现：{}", "页面大小超过最大值18，疑似恶意访问！");
            pageDto.setPageSize(maxPageSize);
        }
        // 获取文章列表，按推荐权重与创建时间降序显示
        LambdaQueryWrapper<Articleinfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Articleinfo::getArticlestate, 0);
        wrapper.orderByDesc(Articleinfo::getIstop, Articleinfo::getCreatetime);
        Page<Articleinfo> page = new Page<>(pageDto.getPageNum(), pageDto.getPageSize());
        page(page, wrapper);
        List<Articleinfo> list = page.getRecords();
        // 将信息封装到ArticleVo中
        List<ArticleVo> articleVos = new ArrayList<>();
        list.forEach(articleinfo -> {
            // 调用setArticleVo将数据设置进去
            ArticleVo articleVo = setArticleVo(articleinfo);
            articleVos.add(articleVo);
        });
        log.info("ArticleinfoServiceImpl.articlePage业务结束，结果为：{}", articleVos.size());
        return ResponseResult.okResult(articleVos,page.getTotal());

    }

    /**
     * 热门文章
     * 获取游览量最高的前五篇文章
     *
     * @return {@link ResponseResult}<{@link List}<{@link HotArticleVo}>>
     */
    @Override
    public ResponseResult<List<HotArticleVo>> hotArticle() {

        Page<Review> reviewPage = new Page<>(1, 5);
        LambdaQueryWrapper<Review> reviewQW = new LambdaQueryWrapper<>();
        reviewQW.orderByDesc(Review::getReview);
        reviewService.page(reviewPage, reviewQW);

        List<Review> reviews = reviewPage.getRecords();
        List<HotArticleVo> hotArticleVos = new ArrayList<>();
        reviews.forEach(review -> {
            HotArticleVo hotArticleVo = new HotArticleVo();
            Articleinfo articleinfo = getById(review.getArticleid());
            hotArticleVo.setId(articleinfo.getId());
            hotArticleVo.setArticletitle(articleinfo.getArticletitle());
            hotArticleVo.setReview(review.getReview());
            hotArticleVos.add(hotArticleVo);
        });
        log.info("ArticleinfoServiceImpl.hotArticle业务结束，结果为：{}", hotArticleVos);
        return ResponseResult.okResult(hotArticleVos);
    }

    /**
     * 时间线文章
     * 只返回前五篇 按时间戳降序 返回最新的五篇
     *
     * @return {@link ResponseResult}<{@link List}<{@link TimeArticleVo}>>
     */
    @Override
    public ResponseResult<List<TimeArticleVo>> timeArticles() {

        Page<Articleinfo> page = new Page<>(1, 5);
        lambdaQuery().orderByDesc(Articleinfo::getCreatetime).page(page);
        List<TimeArticleVo> timeArticleVos = new ArrayList<>();
        for (Articleinfo record : page.getRecords()) {
            TimeArticleVo timeArticleVo = new TimeArticleVo();
            timeArticleVo.setId(record.getId());
            timeArticleVo.setArticletitle(record.getArticletitle());
            timeArticleVo.setCreatetime(record.getCreatetime());

            Admin admin = adminService.getById(record.getCreateby());
            String name = "无名氏";
            if (admin != null) {
                name = admin.getUsername();
            }
            timeArticleVo.setCreatebyName(name);
            timeArticleVos.add(timeArticleVo);
        }
        log.info("ArticleinfoServiceImpl.timeArticles业务结束，结果为：{}", timeArticleVos);
        return ResponseResult.okResult(timeArticleVos);
    }

    /**
     * 通过文章id查询文章详情
     *
     * @param id id
     * @return {@link ResponseResult}<{@link ArticleVo}>
     */
    @Override
    public ResponseResult<ArticleVo> articleDetailById(Long id) {
        Articleinfo articleinfo = getById(id);
        if (articleinfo == null) {
            return ResponseResult.errorResult(5001, "请求有误，找不到该文章！");
        }
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(articleinfo.getId());
        articleVo.setArticletitle(articleinfo.getArticletitle());
        articleVo.setArticleaccount(articleinfo.getArticleaccount());

        Category category = categoryService.getById(articleinfo.getCategoryid());
        String categoryName = "未知分类";
        if (category != null) {
            categoryName = category.getCategoryName();
        }
        articleVo.setCategory(categoryName);

        articleVo.setArticleimgurl(articleinfo.getArticleimgurl());
        articleVo.setArticlecontent(articleinfo.getArticlecontent());

        Admin admin = adminService.getById(articleinfo.getCreateby());
        String name = "无名氏";
        if (admin != null) {
            name = admin.getUsername();
        }

        articleVo.setCreatebyName(name);
        articleVo.setCreatetime(articleinfo.getCreatetime());
        // 根据文章id 查询游览量 若不存在则游览量为0
        LambdaQueryWrapper<Review> reviewQw = new LambdaQueryWrapper<>();
        reviewQw.eq(Review::getArticleid, articleinfo.getId());
        Review review = reviewService.getOne(reviewQw);
        Long reviewSum = 0L;
        if (review != null) {
            reviewSum = review.getReview();
        }
        articleVo.setReview(reviewSum);
        log.info("ArticleinfoServiceImpl.articleDetailById业务结束，结果为：{}", articleVo);
        return ResponseResult.okResult(articleVo);
    }

    /**
     * 按类别查找文章列表
     *
     * @param byCategoryDto 按类别dto
     * @return {@link ResponseResult}<{@link List}<{@link ArticleVo}>>
     */
    @Override
    public ResponseResult<List<ArticleVo>> articleListByCategory(ArticleListByCategoryDto byCategoryDto) {
        if (byCategoryDto.getCategoryId()<1 || byCategoryDto.getCategoryId() ==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.ERROR);
        }
        LambdaQueryWrapper<Articleinfo> articleQw = new LambdaQueryWrapper<>();
        articleQw.eq(Articleinfo::getCategoryid, byCategoryDto.getCategoryId());
        Page page = new Page(byCategoryDto.getPageNum(), byCategoryDto.getPageSize());
        page(page, articleQw);
        List<Articleinfo> list = page.getRecords();
        List<ArticleVo> articleVos = new ArrayList<>();
        list.forEach(articleinfo -> {
            ArticleVo articleVo = setArticleVo(articleinfo);
            articleVos.add(articleVo);
        });
        log.info("ArticleinfoServiceImpl.articleListByCategory业务结束，结果为：{}",articleVos.size());
        return ResponseResult.okResult(articleVos,page.getTotal());
    }


    public ArticleVo setArticleVo(Articleinfo articleinfo) {
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(articleinfo.getId());
        articleVo.setArticletitle(articleinfo.getArticletitle());
        articleVo.setArticleaccount(articleinfo.getArticleaccount());
        articleVo.setCategory(null);
        articleVo.setArticleimgurl(articleinfo.getArticleimgurl());
        // 根据文章创建者id 查询创建者名称，若不存在设置为无名氏
        Admin admin = adminService.getById(articleinfo.getCreateby());
        if (admin == null) {
            articleVo.setCreatebyName("无名氏");
        } else {
            articleVo.setCreatebyName(admin.getUsername());
        }
        articleVo.setCreatetime(articleinfo.getCreatetime());
        // 根据文章id 查询游览量 若不存在则游览量为0
        LambdaQueryWrapper<Review> reviewQW = new LambdaQueryWrapper<>();
        reviewQW.eq(Review::getArticleid, articleinfo.getId());
        Review review = reviewService.getOne(reviewQW);
        Long reviewSum = 0L;
        if (review != null) {
            reviewSum = review.getReview();
        }
        articleVo.setReview(reviewSum);
        // 根据文章id查询评论数量，若为空 则数量为0
        LambdaQueryWrapper<Comment> Commentqw = new LambdaQueryWrapper<>();
        Commentqw.eq(Comment::getArticleid, articleinfo.getId());
        long count = commentService.count(Commentqw);
        if (count > 0) {
            articleVo.setCommentSum(count);
        } else {
            articleVo.setCommentSum(0L);
        }

        // 设置文章分类名
        Category category = categoryService.getById(articleinfo.getCategoryid());
        articleVo.setCategory(category.getCategoryName());

        articleVo.setIstop(articleinfo.getIstop());
        return articleVo;
    }
}
