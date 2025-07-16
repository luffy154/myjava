package com.report.test.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import java.io.File;

/**
 * @ClassName:EmailService
 * @author: qm
 * @Description:
 * @date:2025-05-27
 */
@Service
public class EmailService {

    @Resource
    private JavaMailSender mailSender;

//    @Resource
//    private DataSource dataSource;

    @Value("${spring.mail.primary.username}")
    private String fromEmail;

    /**
     * 发送简单文本邮件
     */
    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

    /**
     * 发送HTML格式邮件
     */
    public void sendHtmlEmail(String to, String subject, String htmlContent)
            throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // 第二个参数true表示HTML内容

        mailSender.send(message);
    }

    /**
     * 发送带附件的邮件
     */
    public void sendEmailWithAttachment(String to, String subject, String text,
                                        String attachmentFileName, File file)
            throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        // 添加附件
        FileSystemResource resource = new FileSystemResource(file);
        helper.addAttachment(attachmentFileName, resource);

        mailSender.send(message);
    }

//    public void test(){
//        Object mainConnection = TransactionSynchronizationManager.getResource(dataSource);
//        TransactionSynchronizationManager.bindResource(dataSource, mainConnection);
//
//        TransactionSynchronizationManager.unbindResource(dataSource);
//    }
}

