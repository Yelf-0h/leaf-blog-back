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
import com.yecheng.leafblogback.utils.KodoOssUtil;
import com.yecheng.leafblogback.utils.SystemConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@SpringBootTest
@Slf4j
class LeafBlogBackApplicationTests {

    @Resource
    private ArticleinfoService articleinfoService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private KodoOssUtil kodoOssUtil;


    @Test
    public void contextLoads() {
        List<Articleinfo> list = articleinfoService.list();
        log.info("LeafBlogBackApplicationTests.contextLoads业务结束，结果为：{}", list);
    }

    @Test
    void redisTest() {
        Long add = stringRedisTemplate.opsForSet().add("108", "108Test");
        log.info("LeafBlogBackApplicationTests.redisTest业务结束，结果为：{}", add);
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

    @Test
    void Thread() {

        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService1 = Executors.newFixedThreadPool(10);
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(10));
        for (int i = 0; i < 100; i++) {
            executorService.execute(new MyThread(i));
        }
    }

    @Test
    void upload() {
        KodoOssUtil kodoOssUtil = new KodoOssUtil();
        File file = new File("https://fastdfs-gateway.ys7.com/3cea/1/capture/003iT6EU2XtMBOgbYRinSfn9QCQXvXy.jpg?Expires=1680241497&OSSAccessKeyId=LTAIzI38nEHqg64n&Signature=AT09jZmb3eNqeuSUTlHGr%2Bn%2BiO8%3D");

        try {
            InputStream inputStream = new FileInputStream(file);
            String upload = kodoOssUtil.upload("asdadasd.jpg", inputStream);
            System.out.println(upload);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    class MyThread implements Runnable {
        int i=0;

        public MyThread(int i) {
            this.i = i;
        }

        @Override
        public void run() {

            System.out.println(Thread.currentThread().getName() + "--->" + i);
            try {
                Thread.sleep(1000L);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
