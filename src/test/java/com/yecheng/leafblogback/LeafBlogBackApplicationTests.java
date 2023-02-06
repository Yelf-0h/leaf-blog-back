package com.yecheng.leafblogback;

import com.yecheng.leafblogback.entity.Articleinfo;
import com.yecheng.leafblogback.entity.User;
import com.yecheng.leafblogback.service.ArticleinfoService;
import com.yecheng.leafblogback.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@Slf4j
class LeafBlogBackApplicationTests {

    @Resource
    private ArticleinfoService articleinfoService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Test
    public void contextLoads() {
        List<Articleinfo> list = articleinfoService.list();
        log.info("LeafBlogBackApplicationTests.contextLoads业务结束，结果为：{}",list);
    }

    @Test
    void redisTest() {
        Long add = stringRedisTemplate.opsForSet().add("108", "108Test");
        log.info("LeafBlogBackApplicationTests.redisTest业务结束，结果为：{}",add);
    }
}
