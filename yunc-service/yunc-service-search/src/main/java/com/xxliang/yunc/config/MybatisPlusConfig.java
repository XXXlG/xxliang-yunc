package com.xxliang.yunc.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author xxliang
 * @date 2025/5/29  18:43
 * @description MP配置
 */
@Configuration
@MapperScan("com.xxliang.yunc.mapper")
@EnableTransactionManagement
public class MybatisPlusConfig {

    /**
     * 分页插件。
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
