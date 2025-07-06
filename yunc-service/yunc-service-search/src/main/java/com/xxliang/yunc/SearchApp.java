package com.xxliang.yunc;

import org.assertj.core.internal.bytebuddy.build.ToStringPlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author xxliang
 * @date 2025/6/7  23:30
 * @description
 */
//排除数据源配置
//@EnableElasticsearchRepositories
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SearchApp {
    public static void main(String[] args) {
        SpringApplication.run(SearchApp.class, args);
    }
}
