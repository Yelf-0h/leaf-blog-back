package com.yecheng.leafblogback;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.yecheng.leafblogback.bean.entity.Articleinfo;
import com.yecheng.leafblogback.service.ArticleinfoService;
import com.yecheng.leafblogback.utils.SystemConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @SpringBootTest
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

    @Test
    void jwtc() {
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put("uid", Integer.parseInt("123"));
                put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
            }
        };

        String token = JWTUtil.createToken(map, "1234".getBytes());
        System.out.println(token);
    }

    @Test
    void jwtp() {
        String rightToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6IuWwj-adjuWtkCIsInVzZXJJZCI6MiwiZW1haWwiOiIyODE3ODU3MzczQHFxLmNvbSJ9.xgd8K11M6tSaFf7ystEber1q85QKayb03uV9EF0ARvY";

        JWT jwt = JWTUtil.parseToken(rightToken);
        JSONObject claimsJson1 = jwt.getPayload().getClaimsJson();
        boolean verify = JWTUtil.verify(rightToken, SystemConstant.TOKEN_KEY.getBytes());
        System.out.println(claimsJson1.get("userId"));
        System.out.println(claimsJson1);
        System.out.println(verify);
    }

    @Test
    void sendEmail() {
        MailUtil.send("2817857373@qq.com", "测试", "邮件来自Hutool测试", false);
    }

    @Test
    void name() {
        int[] ints = NumberUtil.generateRandomNumber(0, 999999, 1);

    }

    @Test
    void md5() {
        String s = SecureUtil.md5("123");
        System.out.println(s);
    }

    @Test
    void jwttest() {
        HashMap<String, Object> map = new HashMap<>(6);
        map.put("userId", 1L);
        map.put("userName", "张三");
        map.put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24);
        HashMap<String, Object> map2 = new HashMap<>(6);
        map.put("userId", 1L);
        map.put("userName", "张三");
        map.put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24);
        String token = JWTUtil.createToken(map, SystemConstant.TOKEN_KEY.getBytes());
        String token2 = JWTUtil.createToken(map2, SystemConstant.TOKEN_KEY.getBytes());
        System.out.println(token2.equals(token));
    }
}
