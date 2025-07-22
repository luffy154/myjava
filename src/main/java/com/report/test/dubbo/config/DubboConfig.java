package com.report.test.dubbo.config;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName:RedisConfig
 * @author: qm
 * @Description:
 * @date:2025-07-16
 */
@Configuration
@ComponentScan(basePackages = {"com.report.test.dubbo"})
@EnableAutoConfiguration
@DubboComponentScan(basePackages = {"com.report.test.dubbo"})
public class DubboConfig {
    @Bean
    public String test() {
        return "aaa";
    }

}
