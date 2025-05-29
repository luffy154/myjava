package com.report.test.mail;

import lombok.Data;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName:MutiEmailConfig
 * @author: qm
 * @Description:
 * @date:2025-05-27
 */
@Component
@ConfigurationProperties(prefix = "custom")
@Data
public class MutiEmailConfig {
    private Map<String, MailProperties> mail;
}
