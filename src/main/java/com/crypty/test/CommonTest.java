package com.crypty.test;

import com.alibaba.fastjson2.JSON;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:CommonTest
 * @author: qm
 * @Description:
 * @date:2024-12-16
 */
public class CommonTest {
    public static void main(String[] args) {
        //我的token。 1737618266.cIYLqSxQrBE1H8wiy9RG8Qn1EjUGxMUS.e320c2eb50acf2f2
        //刘海峰token。 1737617077.MuQ1cOv0gaS/590g/BhEM6jTOk4JC99v.4e59187a4dd51c17

        /**
         * 1941761333.2SVwknzbreJB7mCEfoYSwV6F1jr17ZUytLFYqxwD7os=.2974d2e4573e573b
         *   时间戳         userId密文                                签名
         *   1838150700010491904|267392
         *
         *   1742200426.7b/z0sgncRVryYglhLqBhwSLf/5wqwA2m3dGTBy0qZM=.1d7a80e1355b99ad
         *
         *   2141761333.7b/z0sgncRVryYglhLqBhwSLf/5wqwA2m3dGTBy0qZM=.d2b8f2b068c06880
         */
        String token = "2141761333.7b/z0sgncRVryYglhLqBhwSLf/5wqwA2m3dGTBy0qZM=.c225c1dfa5c71927";
        System.out.println(EncryptUtil.encrypt16("2141761333"+"1901552529650229248|267392"));

        Map map= EncryptUtil.checkToken(token, "15acd2dfz");
        System.out.println(JSON.toJSONString(map));
        System.out.println(Long.MAX_VALUE);
        String data="[20051920240517000079,20051920240517000078]";
        List<BigInteger> list = JSON.parseArray(data,BigInteger.class);
        System.out.println(list);

        System.out.println(Integer.parseInt("१२३"));
    }
}
