package com.report.test.redis;

import com.alicp.jetcache.anno.EnableCache;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.alicp.jetcache.anno.support.GlobalCacheConfig;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName:RedisConfig
 * @author: qm
 * @Description:
 * @date:2025-07-16
 */
@Configuration
@ComponentScan(basePackages = {"com.report.test.redis"})
@EnableAutoConfiguration
@EnableMethodCache(basePackages = "com.report.test.redis")
public class RedisConfig {
    @Bean
    public String test() {
        return "aaa";
    }

//    @Bean
//    public GlobalCacheConfig config(GlobalCacheConfig config) {
//        return config;
//    }
}
