package com.param.test;

import com.alibaba.fastjson2.JSON;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @ClassName:ExcelTest
 * @author: qm
 * @Description:
 * @date:2025-03-27
 */
public class ExcelTest {
    public static void main(String[] args) {
        try {
            Path p = Paths.get(ParamGeneral.class.getClassLoader().getResource("excel.txt").getPath());
            String content = new String(Files.readAllBytes(p));
            List<String> lines  = Arrays.asList(content.split("\n"));
            System.out.println(lines.size());
            Map<String,List<String>> map = new TreeMap<>();
            for (String line : lines) {
                String[] ss =line.split("\t");
                map.putIfAbsent(ss[0],new ArrayList<>());
                map.get(ss[0]).add(ss[1]);
            }
            map.forEach((k,v)->{
                System.out.println(k+"\t"+String.join("\t",v));
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
