package com.report.test.redis;

import com.alibaba.fastjson2.JSON;
import com.report.test.dubbo.consumer.ProductConsumer;
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
public class RedisStarter {
    public static void main(String[] args) throws IOException {
        // 可选：添加环境配置
        ConfigurableEnvironment env = new StandardEnvironment();
        env.getPropertySources().addFirst(
                new ResourcePropertySource("classpath:application-redis.yml")
        );
        ConfigurableApplicationContext context = new SpringApplicationBuilder(RedisConfig.class)
                .web(WebApplicationType.NONE) // 非Web环境
                .properties("spring.config.name=application-redis")
                .environment(env)
                .run(args);
        UserService userService = context.getBean(UserService.class);
        User user1 = userService.getUserById(1);
        System.out.println(JSON.toJSONString(user1));
        System.out.println(JSON.toJSONString(userService.getUserById(1)));
        User user = new User();
        user.setId(1L);
        user.setAge(30);
        user.setName("sy");
//        userService.updateUser(user);

//        userService.deleteUser(1L);
    }
}
