package com.async.test;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * @ClassName:AkkaMain
 * @author: qm
 * @Description:
 * @date:2025-04-27
 */
public class AkkaMain {
    public static void main(String[] args) {
        System.out.println(LocalDateTime.of(2025, 4, 27, 0, 0).getDayOfWeek().getValue());
        DemoBean demoBean = new DemoBean();
        Object obj = null;
        demoBean.setName((String)obj);
        System.out.println(demoBean.getName()==null);
        for (int j=0;j<10;j++) {
            int oneCount = 0;
            Random random = new Random();
            for (int i = 0; i < 10000; i++) {
                if (random.nextInt(2) == 0) {
                    oneCount++;
                }
            }
            System.out.println(oneCount);
        }
    }

    @Data
    public static class DemoBean{
        private String name;
    }
}
