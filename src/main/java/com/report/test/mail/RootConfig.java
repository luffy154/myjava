package com.report.test.mail;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName:RootConfig
 * @author: qm
 * @Description:
 * @date:2025-05-27
 */
@Configuration
@ComponentScan(basePackages = {"com.report.test","org.springframework.mail"}) // 启用组件扫描
@EnableAutoConfiguration
@EnableConfigurationProperties
public class RootConfig {
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.mail.primary")
    public JavaMailSender primaryMailSender() {
        return new JavaMailSenderImpl();
    }

    @Bean
    public Map<String, JavaMailSender> mailSenders(MutiEmailConfig mailAccountConfig) {
        Map<String, JavaMailSender> senderMap = new HashMap<>();

        mailAccountConfig.getMail().forEach((key, account) -> {
            JavaMailSenderImpl sender = new JavaMailSenderImpl();
            sender.setHost(account.getHost());
            sender.setPort(account.getPort());
            sender.setUsername(account.getUsername());
            sender.setPassword(account.getPassword());
            sender.setProtocol(account.getProtocol());

            // 转换properties
            Properties javaMailProps = new Properties();
            javaMailProps.putAll(account.getProperties());
            sender.setJavaMailProperties(javaMailProps);

            senderMap.put(key, sender);
        });

        return senderMap;
    }
}
