package com.xxliang.yunc.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import io.seata.rm.datasource.DataSourceProxy;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author xxliang
 * @date 2025/6/1  14:02
 * @description DataSourceConfig:将datasource交给seata管理
 */
@Configuration
public class DataSourceConfiguration {

    //mapper.xml路径
    @Value("${mybatis-plus.mapper-locations}")
    private String mapperLocations;

    //手动配置bean
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource druidDataSource(){
        //自己创建一个Bean交给容器
        return new DruidDataSource();
    }

    //配置MybatisSqlSessionFactoryBean
    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactoryBean(DataSourceProxy dataSourceProxy) throws Exception {
        //处理MybatisPlus  : MybatisSqlSessionFactoryBean是帮我们创建sqlsession的，
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSourceProxy);
        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        //事务管理工厂
        factory.setTransactionFactory(new SpringManagedTransactionFactory());
        return factory;
    }

    @Primary
    @Bean("dataSource")
    public DataSourceProxy dataSourceProxy(DataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }

}