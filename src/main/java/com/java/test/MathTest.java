package com.java.test;

import org.python.google.common.collect.Lists;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName:MathTest
 * @author: qm
 * @Description:
 * @date:2025-02-17
 */
public class MathTest {
    public static void main(String[] args) {
        double a = 3.0;
        System.out.println(Math.sqrt(a));
        String code="805005340110701";
        System.out.println(code.substring(8,13));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        System.out.println(formatter.format(LocalDateTime.now()));

        List<String> list= Lists.newArrayList("4","2","3");
        List<Boolean> result = list.stream().map(t->t.equals("1")).collect(Collectors.toList());
        System.out.println(result.contains(true));
        System.out.println(3&1);


        LocalDate localDate = LocalDate.parse("2025-06-20");
        System.out.println(localDate.minusDays(1).toString());
    }
}
