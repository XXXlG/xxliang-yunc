package com.xxliang.yunc.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import javax.annotation.Resource;

/**
 * @author xxliang
 * @date 2025/6/4  14:49
 * @description
 */
@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    @Resource
    private RedisConnectionFactory factory;

    /**
     * 自定义生成redis-key ， 类名.方法名
     */
    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(o.getClass().getName()).append(".");
            sb.append(method.getName()).append(".");
            for (Object obj : objects) {
                sb.append(obj.toString());
            }
            System.out.println("keyGenerator=" + sb.toString());
            return sb.toString();
        };
    }

    @Bean
    @Override
    public CacheResolver cacheResolver() {
        return new SimpleCacheResolver(cacheManager());
    }

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        // 用于捕获从Cache中进行CRUD时的异常的回调处理器。在做缓存处理时如果抛异常,会调用此方法
        return new CacheErrorHandler() {
            @Override  //  缓存获取异常
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                //人工补偿
                System.out.println("handleCacheGetError--key=" + key);
            }

            @Override  // 缓存更新异常
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                //人工补偿
                System.out.println("handleCachePutError--key=" + key + " value=" + value);
            }

            @Override // 缓存删除异常
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
                //人工补偿
            }
            @Override // 缓存清除异常
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                //人工补偿
                System.out.println("handleCacheClearError");
            }
        };
    }
    //缓存管理器
    @Bean
    @Override
    public CacheManager cacheManager() {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues() //不允许空值
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        //值使用JSON序列化
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()));
        return RedisCacheManager.builder(factory).cacheDefaults(cacheConfiguration).build();
    }
}