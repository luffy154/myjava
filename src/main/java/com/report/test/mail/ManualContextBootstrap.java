package com.report.test.mail;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.report.test.BillInfo;
import com.report.test.MailInfo;
import com.report.test.ManualSpringStartup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:ManualContextBootstrap
 * @author: qm
 * @Description:
 * @date:2025-05-27
 */
public class ManualContextBootstrap {
    public static void main(String[] args) {
        // 创建注解配置上下文
//        AnnotationConfigApplicationContext context =
//                new AnnotationConfigApplicationContext();
        new SpringApplicationBuilder(RootConfig.class)
                .web(WebApplicationType.NONE) // 非Web环境
                .run(args);

        ConfigurableApplicationContext context =
                SpringApplication.run(RootConfig.class, args);

        try {
            // 可选：添加环境配置
            ConfigurableEnvironment env = new StandardEnvironment();
            env.getPropertySources().addFirst(
                    new ResourcePropertySource("classpath:application.yml")
            );
            context.setEnvironment(env);

            // 验证Bean加载
            System.out.println("已加载Bean数量: " + context.getBeanDefinitionCount());

            // 4. 获取 Bean 并使用
            SpringTemplateEngine engine = context.getBean(SpringTemplateEngine.class);
            Context thymeleafContext = new Context();

            MailInfo mailInfo = new MailInfo();
            mailInfo.setSendCompanyName("江苏和府餐饮管理有限公司");
            mailInfo.setSendDate("2025-05-23");
            mailInfo.setReceiveCompanyName("武汉仁耀谦信息科技有限公司");
            mailInfo.setCodeNum("2027Q100");
            List<BillInfo> billInfo = new ArrayList<>();
            BillInfo billInfo1 = new BillInfo();
            billInfo1.setEndDate("2025-05-23");
            billInfo1.setDebitAmount("231231312.00");
            billInfo1.setRemark("测试");
            billInfo.add(billInfo1);

            BillInfo billInfo2 = new BillInfo();
            billInfo2.setEndDate("2025-05-24");
            billInfo2.setDebitAmount("3324234233.00");
            billInfo2.setRemark("测试");
            billInfo.add(billInfo2);

            BillInfo billInfo3 = new BillInfo();
            billInfo3.setEndDate("2025-05-29");
            billInfo3.setDebitAmount("3242342342.00");
            billInfo3.setRemark("测试");
            billInfo.add(billInfo3);

            mailInfo.setBillInfo(billInfo);

            thymeleafContext.setVariables((JSONObject) JSON.toJSON(mailInfo));


            String html = engine.process("template", thymeleafContext);
            System.out.println(html);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try{
                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.useFastMode();
                String baseUri = Paths.get("src/main/resources/").toUri().toString();
                builder.withHtmlContent(html, baseUri);
                builder.useFont(new File(ManualSpringStartup.class.getClassLoader().getResource("fonts/NotoSansSC-Regular.ttf").getPath())
                        , "SourceHanSans",400,
                        BaseRendererBuilder.FontStyle.NORMAL,
                        true);
                builder.useFont(new File(ManualSpringStartup.class.getClassLoader().getResource("fonts/NotoSansSC-Bold.ttf").getPath())
                        , "SourceHanSans",700,
                        BaseRendererBuilder.FontStyle.NORMAL,
                        true);
                builder.toStream(os);
                builder.run();
            }catch (Exception e) {

            }



            // 执行业务逻辑
            MultiEmailService service = context.getBean(MultiEmailService.class);
            service.sendHtmlEmail("qianmiao@hf-lm.com",
                    "测试邮件",
                    "<div>这是一封测试邮件</div>",
                    "test.pdf", new ByteArrayResource(os.toByteArray()), "feishu-mail");

//            MutiEmailConfig config = context.getBean(MutiEmailConfig.class);
//            System.out.println(config);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            context.close();
        }
    }
}
