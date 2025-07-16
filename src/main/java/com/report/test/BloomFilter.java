package com.report.test;

import com.google.common.hash.Hashing;
import com.report.test.mail.RootConfig;
import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName:BloomFilter
 * @author: qm
 * @Description:
 * @date:2025-07-16
 */
@Component
@Data
public class BloomFilter {
    private RBloomFilter<String> bloomFilter1;
    private RBloomFilter<String> bloomFilter2;
    private RBloomFilter<String> bloomFilter3;

    public static void main(String[] args) throws IOException {
        new SpringApplicationBuilder(RedisConfig.class)
                .web(WebApplicationType.NONE) // 非Web环境
                .run(args);

        ConfigurableApplicationContext context =
                SpringApplication.run(RedisConfig.class, args);

        // 可选：添加环境配置
        ConfigurableEnvironment env = new StandardEnvironment();
        env.getPropertySources().addFirst(
                new ResourcePropertySource("classpath:application.yml")
        );
        context.setEnvironment(env);

        BloomFilter bloomFilter = context.getBean(BloomFilter.class);

        bloomFilter.add("aaaaaaaa");

        System.out.println(bloomFilter.contains("aaaaaaaa"));
        System.out.println(bloomFilter.contains("bbbbbbbb"));
    }

    @Resource
    public void setBloomFile(Redisson redisson) {
        bloomFilter1 = redisson.getBloomFilter("aaaaaaaa");
        bloomFilter1.tryInit(10000, 0.01);
        bloomFilter2 = redisson.getBloomFilter("bbbbbbbb");
        bloomFilter2.tryInit(10000, 0.01);
        bloomFilter3 = redisson.getBloomFilter("cccccccc");
        bloomFilter3.tryInit(10000, 0.01);
    }

    public void add(String value) {
        bloomFilter1.add(value);
        bloomFilter2.add(String.valueOf(value.hashCode()));
        bloomFilter3.add(String.valueOf(Hashing.sha256().hashString(value, StandardCharsets.UTF_8).asLong()));
    }

    public boolean contains(String value) {
        return bloomFilter1.contains(value) && bloomFilter2.contains(String.valueOf(value.hashCode())) && bloomFilter3.contains(String.valueOf(Hashing.sha256().hashString(value, StandardCharsets.UTF_8).asLong()));
    }
}
