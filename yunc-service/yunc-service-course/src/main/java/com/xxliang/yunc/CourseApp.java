package com.xxliang.yunc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xxliang
 * @date 2025/6/3  20:07
 * @description
 */
@SpringBootApplication
@EnableCaching
@EnableFeignClients
public class CourseApp {
    public static void main(String[] args) {
        SpringApplication.run(CourseApp.class, args);
    }
}
