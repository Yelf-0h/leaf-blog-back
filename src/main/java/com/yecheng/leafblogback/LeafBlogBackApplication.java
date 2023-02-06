package com.yecheng.leafblogback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 凌云博客启动类
 *
 * @author Yefl
 * @date 2023/02/02
 */
@SpringBootApplication
@MapperScan("com.yecheng.leafblogback.mapper")
@EnableCaching
public class LeafBlogBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeafBlogBackApplication.class, args);
    }

}
