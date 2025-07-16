package com.report.test;

import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName:RedisConfig
 * @author: qm
 * @Description:
 * @date:2025-07-16
 */
@Configuration
@ComponentScan(basePackages = {"com.report.test","org.springframework."})
@EnableAutoConfiguration
public class RedisConfig {
    @Bean
    public Redisson redisson(RedisProperties redisConfigProperties) {
        Config config = new Config();
        if (Objects.nonNull(redisConfigProperties.getCluster())) {
            config.useClusterServers().setPassword(redisConfigProperties.getPassword()).setNodeAddresses(redisConfigProperties.getCluster().getNodes().stream()
                    .map(item -> "redis://" + item).collect(Collectors.toList()));
        } else {
            config.useSingleServer().setAddress("redis://" + redisConfigProperties.getHost() + ":" + redisConfigProperties.getPort())
                    .setPassword(redisConfigProperties.getPassword())
                    .setDatabase(redisConfigProperties.getDatabase());
        }
        config.setThreads(2);
        config.setNettyThreads(2);
        return (Redisson) Redisson.create(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplateString(RedisConnectionFactory factory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setValueSerializer(new StringRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }
}
