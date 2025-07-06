package com.xxliang.yunc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xxliang
 * @date 2025/6/22  16:38
 * @description
 */
@SpringBootApplication
@EnableFeignClients
public class PayApp {
    public static void main(String[] args) {
        SpringApplication.run(PayApp.class, args);
    }
}
