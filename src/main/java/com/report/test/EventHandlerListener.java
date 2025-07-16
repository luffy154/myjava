package com.report.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @ClassName:EventListener
 * @author: qm
 * @Description:
 * @date:2025-06-23
 */
@Component
@Slf4j
public class EventHandlerListener {
    @EventListener(ApplicationReadyEvent.class)
    public void handleApplicationReadyEvent() throws Exception {
        log.info("Application Startup End. copy file");
        InputStream bold = EventHandlerListener.class.getClassLoader().getResourceAsStream("fonts/NotoSansSC-Bold.ttf");
        InputStream regular = EventHandlerListener.class.getClassLoader().getResourceAsStream("fonts/NotoSansSC-Regular.ttf");
        try {
            Path regularPath = Paths.get("fonts/NotoSansSC-Regular.ttf");
            // 判断目标文件是否存在
            if (!Files.exists(regularPath)) {
                // 不存在则创建父目录并复制文件
                Files.createDirectories(regularPath.getParent());

                if (regular != null) {
                    Files.copy(regular, regularPath, StandardCopyOption.REPLACE_EXISTING);
                    regular.close();
                }

                log.info("文件已复制至:{}", regularPath);
            } else {
                log.info("文件已存在，无需复制。");
            }
            Path boldPath = Paths.get("fonts/NotoSansSC-Bold.ttf");
            // 判断目标文件是否存在
            if (!Files.exists(boldPath)) {
                // 不存在则创建父目录并复制文件
                Files.createDirectories(boldPath.getParent());

                if (bold != null) {
                    Files.copy(bold, boldPath, StandardCopyOption.REPLACE_EXISTING);
                }

                log.info("文件已复制至:{}", boldPath);
            } else {
                log.info("文件已存在，无需复制。");
            }
        } catch (IOException e) {
            log.error("文件操作失败", e);
        } finally {
            if (regular != null) {
                regular.close();
            }
            if (bold != null) {
                bold.close();
            }
        }

        log.info("copy file End");
    }
}
