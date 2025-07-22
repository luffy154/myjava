package com.report.test.dubbo;

import com.report.test.BloomFilter;
import com.report.test.RedisConfig;
import com.report.test.dubbo.config.DubboConfig;
import com.report.test.dubbo.consumer.ProductConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

/**
 * @ClassName:DubboStarter
 * @author: qm
 * @Description:
 * @date:2025-07-21
 */
public class DubboStarter {
    public static void main(String[] args) throws IOException {
        // 可选：添加环境配置
        ConfigurableEnvironment env = new StandardEnvironment();
        env.getPropertySources().addFirst(
                new ResourcePropertySource("classpath:application-dubbo.yml")
        );
        ConfigurableApplicationContext context = new SpringApplicationBuilder(DubboConfig.class)
                .web(WebApplicationType.NONE) // 非Web环境
                .environment(env)
                .run(args);

        System.out.println(context.getBean(ProductConsumer.class).getProduct());
    }
}
