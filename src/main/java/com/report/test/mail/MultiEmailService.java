package com.report.test.mail;

import com.report.test.ManualSpringStartup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName:EmailService
 * @author: qm
 * @Description:
 * @date:2025-05-27
 */
@Service
public class MultiEmailService {

    @Resource
    private Map<String, JavaMailSender> mailSenders;

    /**
     * 发送简单文本邮件
     */
    public void sendSimpleEmail(String to, String subject, String text,  String mailSenderName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(((JavaMailSenderImpl)mailSenders.get(mailSenderName)).getUsername());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSenders.get(mailSenderName).send(message);
    }

    /**
     * 发送HTML格式邮件
     */
    public void sendHtmlEmail(String to, String subject, String htmlContent,String attachmentFileName, File file,  String mailSenderName)
            throws MessagingException {
        JavaMailSender mailSender = mailSenders.get(mailSenderName);
        MimeMessage message = mailSender.createMimeMessage();
        message.addHeader("Content-Type", "text/html");
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(((JavaMailSenderImpl)mailSender).getUsername());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // 第二个参数true表示HTML内容
        // 添加附件
        FileSystemResource resource = new FileSystemResource(file);
        helper.addAttachment(attachmentFileName, resource);

        mailSender.send(message);
    }

    public void sendHtmlEmail(String to, String subject, String htmlContent, String attachmentFileName, InputStreamSource source, String mailSenderName)
            throws MessagingException {
        JavaMailSender mailSender = mailSenders.get(mailSenderName);
        MimeMessage message = mailSender.createMimeMessage();
        message.addHeader("Content-Type", "text/html");
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(((JavaMailSenderImpl)mailSender).getUsername());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // 第二个参数true表示HTML内容
        // 添加附件
        helper.addAttachment(attachmentFileName, source);

        mailSender.send(message);
    }

    /**
     * 发送带附件的邮件
     */
    public void sendEmailWithAttachment(String to, String subject, String text,
                                        String attachmentFileName, File file,String mailSenderName)
            throws MessagingException {
        JavaMailSender mailSender = mailSenders.get(mailSenderName);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(((JavaMailSenderImpl)mailSender).getUsername());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        // 添加附件
        FileSystemResource resource = new FileSystemResource(file);
        helper.addAttachment(attachmentFileName, resource);

        mailSender.send(message);
    }
}

