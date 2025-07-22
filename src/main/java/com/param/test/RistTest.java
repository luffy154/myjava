package com.param.test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName:DdddTest
 * @author: qm
 * @Description:
 * @date:2025-03-26
 */
public class RistTest {
    public static void main(String[] args) {
        try {
            Path p = Paths.get(ParamGeneral.class.getClassLoader().getResource("hasUsed.txt").getPath());
            String content = new String(Files.readAllBytes(p));
            List<String> lines  = Arrays.asList(content.split("\n"));
            System.out.println(lines.size());
            Path log = Paths.get(ParamGeneral.class.getClassLoader().getResource("risk.txt").getPath());

            List<String>  logLines = Files.readAllLines( log);
            System.out.println(logLines.size());
            logLines.forEach(line->{
                if(lines.contains( line)){
                    System.out.println( line);
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
