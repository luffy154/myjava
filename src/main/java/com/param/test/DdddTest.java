package com.param.test;

import com.alibaba.fastjson2.JSON;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @ClassName:DdddTest
 * @author: qm
 * @Description:
 * @date:2025-03-26
 */
public class DdddTest {
    public static void main(String[] args) {
        try {
            Path p = Paths.get(ParamGeneral.class.getClassLoader().getResource("ddddd.txt").getPath());
            String content = new String(Files.readAllBytes(p));
            List<String> lines  = Arrays.asList(content.split("\n"));
            System.out.println(lines.size());
            Map<String,String> map = new TreeMap<>();
            for (String line : lines) {
                String[] ss =line.split("\t");
                if(ss.length>=2) {
                    map.put(ss[0], ss[1]);
                }
            }
            System.out.println(JSON.toJSONString(map));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
