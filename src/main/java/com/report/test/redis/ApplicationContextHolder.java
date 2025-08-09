package com.report.test.redis;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName:ApplicationContextHolder
 * @author: qm
 * @Description:
 * @date:2025-08-09
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {
    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext=applicationContext;
    }
}
