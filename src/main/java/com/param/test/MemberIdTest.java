package com.param.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ClassName:DdddTest
 * @author: qm
 * @Description:
 * @date:2025-03-26
 */
public class MemberIdTest {
    public static void main(String[] args) {
        try {
            Path p = Paths.get(ParamGeneral.class.getClassLoader().getResource("memberId.txt").getPath());
            String content = new String(Files.readAllBytes(p));
            List<String> lines  = Arrays.asList(content.split("\n"));
            System.out.println(lines.size());
            Path log = Paths.get(ParamGeneral.class.getClassLoader().getResource("log.txt").getPath());

            List<String>  logLines = Files.readAllLines( log);
            System.out.println(logLines.size());

            System.out.println(logLines.get(0));
            logLines.forEach(line->{
                int index = line.indexOf("memberId\\\":");
                int endindex = line.indexOf(",\\\"memberSystemId\\\"");

                if(lines.contains(line.substring(index+11,endindex))){
                    int deviceIndex = line.indexOf("\\\"x-wx-device-risk-rank\\\"");
                    if(deviceIndex>0) {
                        System.out.println(line.substring(index+11,endindex)+":"+line.substring(deviceIndex, deviceIndex + 30));
                    }else{
                        System.out.println(line.substring(index+11,endindex)+":");
                    }
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
