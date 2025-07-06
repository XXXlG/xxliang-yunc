package com.xxliang.yunc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author xxliang
 * @date 2025/5/30  19:11
 * @description Uaa启动类
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class UaaApp {
    public static void main(String[] args) {
        SpringApplication.run(UaaApp.class, args);
    }
}
