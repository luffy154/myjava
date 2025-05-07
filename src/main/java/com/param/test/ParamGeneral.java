package com.param.test;

import com.alibaba.fastjson2.JSON;
import org.python.google.common.collect.Lists;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ClassName:ParamGeneral
 * @author: qm
 * @Description:
 * @date:2025-03-12
 */
public class ParamGeneral {
    public static void main(String[] args) {
        try {
            Path p = Paths.get(ParamGeneral.class.getClassLoader().getResource("param").getPath());
            String content = new String(Files.readAllBytes(p));
            List<String> lines  = Arrays.asList(content.split("\n"));
            System.out.println(lines.size());
            for(int i=0;i<lines.size()/4;i++){
                System.out.println(lines.get(i*4)+"\t"+lines.get(i*4+1)+"\t"+lines.get(i*4+2)+"\t"+lines.get(i*4+3));
            }
        }catch(Exception e){

        }
    }
}
