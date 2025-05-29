package com.report.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.apache.pdfbox.pdmodel.font.FontFormat;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:ManualSpringStartup
 * @author: qm
 * @Description:
 * @date:2025-05-23
 */
public class ManualSpringStartup {

    public static void main(String[] args) throws IOException {
        // 1. 创建 Spring 上下文（未刷新）
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();

        // 2. 注册配置类
        context.register(AppConfig.class);

        // 3. 刷新容器（初始化所有 Bean）
        context.refresh();

        // 4. 获取 Bean 并使用
        SpringTemplateEngine engine = context.getBean(SpringTemplateEngine.class);
        Context thymeleafContext = new Context();

        MailInfo mailInfo = new MailInfo();
        mailInfo.setSendCompanyName("江苏和府餐饮管理有限公司");
        mailInfo.setSendDate("2025-05-23");
        mailInfo.setReceiveCompanyName("武汉仁耀谦信息科技有限公司");
        mailInfo.setCodeNum("2026Q100");
        List<BillInfo> billInfo = new ArrayList<>();
        BillInfo billInfo1 = new BillInfo();
        billInfo1.setEndDate("2025-05-23");
        billInfo1.setDebitAmount("100.00");
        billInfo1.setRemark("测试");
        billInfo.add(billInfo1);

        BillInfo billInfo2 = new BillInfo();
        billInfo2.setEndDate("2025-05-24");
        billInfo2.setDebitAmount("101.00");
        billInfo2.setRemark("测试1`1");
        billInfo.add(billInfo2);

        BillInfo billInfo3 = new BillInfo();
        billInfo3.setEndDate("2025-05-29");
        billInfo3.setDebitAmount("141.00");
        billInfo3.setRemark("测试");
        billInfo.add(billInfo3);

        mailInfo.setBillInfo(billInfo);

        thymeleafContext.setVariables((JSONObject)JSON.toJSON(mailInfo));


        String html = engine.process("template", thymeleafContext);
        System.out.println(html);



        File  pdfFile = new File("test.pdf");
        OutputStream os = Files.newOutputStream(pdfFile.toPath(), StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.CREATE);
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
        }catch (Exception e){

        }finally {
            os.flush();
            os.close();
        }

        // 5. 关闭上下文（释放资源）
        context.close();
    }
}

