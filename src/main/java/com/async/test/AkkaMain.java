package com.async.test;

import java.time.LocalDateTime;

/**
 * @ClassName:AkkaMain
 * @author: qm
 * @Description:
 * @date:2025-04-27
 */
public class AkkaMain {
    public static void main(String[] args) {
        System.out.println(LocalDateTime.of(2025, 4, 27, 0, 0).getDayOfWeek().getValue());
    }
}
