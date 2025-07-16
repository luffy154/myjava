package com.param.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.math.BigDecimal;
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
public class JSONFileTest {
    public static void main(String[] args) {
        try {
            Path p = Paths.get(ParamGeneral.class.getClassLoader().getResource("json.txt").getPath());
            String content = new String(Files.readAllBytes(p));
            JSONObject jsonObject = JSON.parseObject(content);
            JSONArray jsonArray = jsonObject.getJSONArray("f58q508s");
            BigDecimal real = BigDecimal.ZERO;
            BigDecimal amount = BigDecimal.ZERO;
            for(int i=0;i<jsonArray.size();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject( i);
                real = real.add(jsonObject1.getBigDecimal("psurat"));
                amount = amount.add(jsonObject1.getBigDecimal("psurrf"));
            }
            System.out.println(real+":"+amount);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
