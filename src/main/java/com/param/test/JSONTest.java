package com.param.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;

import java.util.Map;

/**
 * @ClassName:JSONTest
 * @author: qm
 * @Description:
 * @date:2025-03-26
 */
public class JSONTest {
    public static void main(String[] args) {
        String json1 ="{\n" +
                "\t\"traceid\": \"1e36279d8eb658aadd588dbe737b0329\",\n" +
                "\t\"referer\": \"https://servicewechat.com/wx107eaf650c0a4a2d/804/page-frame.html\",\n" +
                "\t\"content-length\": \"370\",\n" +
                "\t\"x-wx-client-ip\": \"113.214.227.230\",\n" +
                "\t\"unionid\": \"oLrXXjgl_PXdNMLcfaCdHPFLgrSU\",\n" +
                "\t\"x-wx-route-tag\": \"f1a08ec0a5bc6f7b4bc577a735b49cb6\",\n" +
                "\t\"x-wx-risk-rank\": \"2\",\n" +
                "\t\"x-forwarded-port\": \"443\",\n" +
                "\t\"x-wx-call-id\": \"1752930231656-71LmYCQ5\",\n" +
                "\t\"userid\": \"80028550012928\",\n" +
                "\t\"x-client-proto\": \"https\",\n" +
                "\t\"x-wx-access-version\": \"1743391934\",\n" +
                "\t\"x-forwarded-host\": \"bmp-weapp.hefunoodles.com\",\n" +
                "\t\"brandid\": \"379517\",\n" +
                "\t\"host\": \"172.20.2.102:9002\",\n" +
                "\t\"content-type\": \"application/json\",\n" +
                "\t\"x-client-proto-ver\": \"HTTP/1.1\",\n" +
                "\t\"x-wx-appid\": \"wx107eaf650c0a4a2d\",\n" +
                "\t\"x-forwarded-proto\": \"https,https\",\n" +
                "\t\"openid\": \"oUF0x5UWsYO968ECPO887za-UpBk\",\n" +
                "\t\"x-wx-device-security-status\": \"Unknown\",\n" +
                "\t\"groupid\": \"267392\",\n" +
                "\t\"x-wx-device-uv-30d\": \"0\",\n" +
                "\t\"x-forwarded-for\": \"113.214.227.230,124.220.126.215,124.220.126.215\",\n" +
                "\t\"x-wx-platform\": \"ios\",\n" +
                "\t\"version\": \"2.0\",\n" +
                "\t\"forwarded\": \"proto=https;host=bmp-weapp.hefunoodles.com;for=\\\"10.1.4.24:12834\\\"\",\n" +
                "\t\"accept\": \"*/*\",\n" +
                "\t\"x-real-ip\": \"124.220.126.215\",\n" +
                "\t\"x-wx-source\": \"wx_client\",\n" +
                "\t\"x-stgw-time\": \"1752930231.640\",\n" +
                "\t\"x-wx-device-risk-rank\": \"0\",\n" +
                "\t\"csession\": \"1752930222.scqsRyOm0yHmtIiSWdxYXknogC6Vfqwj.036f347ac4218bef\",\n" +
                "\t\"traceparent\": \"00-1e36279d8eb658aadd588dbe737b0329-c591e4309826d576-01\",\n" +
                "\t\"x-wx-device-uv-1d\": \"0\",\n" +
                "\t\"x-wx-port\": \"443\",\n" +
                "\t\"x-envoy-expected-rq-timeout-ms\": \"6000\",\n" +
                "\t\"accept-encoding\": \"gzip\",\n" +
                "\t\"user-agent\": \"Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 MicroMessenger/8.0.60(0x18003c32) NetType/WIFI Language/zh_CN MiniProgramEnv/iOS\",\n" +
                "\t\"x-wx-device-uv-7d\": \"0\"\n" +
                "}";
        String json2 ="{\n" +
                "\t\"traceid\": \"1fdec2330f5366c9305ae42a55dad064\",\n" +
                "\t\"content-length\": \"385\",\n" +
                "\t\"referer\": \"https://servicewechat.com/wx107eaf650c0a4a2d/804/page-frame.html\",\n" +
                "\t\"x-wx-client-ip\": \"39.144.222.115\",\n" +
                "\t\"unionid\": \"oLrXXjiENdkat-DMfY0FdH29_mJg\",\n" +
                "\t\"x-wx-route-tag\": \"f1a08ec0a5bc6f7b4bc577a735b49cb6\",\n" +
                "\t\"x-wx-risk-rank\": \"1\",\n" +
                "\t\"x-forwarded-port\": \"443\",\n" +
                "\t\"x-wx-call-id\": \"1752930321275-iBKCoL32\",\n" +
                "\t\"userid\": \"1946480389262852096\",\n" +
                "\t\"x-client-proto\": \"https\",\n" +
                "\t\"x-wx-access-version\": \"1743391934\",\n" +
                "\t\"x-forwarded-host\": \"bmp-weapp.hefunoodles.com\",\n" +
                "\t\"brandid\": \"379517\",\n" +
                "\t\"host\": \"172.20.1.106:9002\",\n" +
                "\t\"content-type\": \"application/json\",\n" +
                "\t\"x-client-proto-ver\": \"HTTP/1.1\",\n" +
                "\t\"x-wx-appid\": \"wx107eaf650c0a4a2d\",\n" +
                "\t\"x-forwarded-proto\": \"https,https\",\n" +
                "\t\"openid\": \"oUF0x5ZKt0BB1KPzHKAkdyu5bePo\",\n" +
                "\t\"x-wx-device-security-status\": \"Unknown\",\n" +
                "\t\"groupid\": \"267392\",\n" +
                "\t\"x-wx-device-uv-30d\": \"0\",\n" +
                "\t\"x-forwarded-for\": \"39.144.222.115,124.220.126.215\",\n" +
                "\t\"x-wx-platform\": \"android\",\n" +
                "\t\"forwarded\": \"proto=https;host=bmp-weapp.hefunoodles.com;for=\\\"124.220.126.215:16374\\\"\",\n" +
                "\t\"accept\": \"*/*\",\n" +
                "\t\"x-real-ip\": \"124.220.126.215\",\n" +
                "\t\"x-wx-source\": \"wx_client\",\n" +
                "\t\"x-stgw-time\": \"1752930321.659\",\n" +
                "\t\"x-wx-device-risk-rank\": \"0\",\n" +
                "\t\"csession\": \"1752930304.wiNx4s98RdcQAIQoyU7bXhGDtyex4suQy49skJCW9UM=.93fa35fffd420695\",\n" +
                "\t\"traceparent\": \"00-1fdec2330f5366c9305ae42a55dad064-2352c1ace5e680f4-01\",\n" +
                "\t\"x-wx-device-uv-1d\": \"0\",\n" +
                "\t\"x-wx-port\": \"443\",\n" +
                "\t\"x-envoy-expected-rq-timeout-ms\": \"6000\",\n" +
                "\t\"accept-encoding\": \"gzip\",\n" +
                "\t\"user-agent\": \"Mozilla/5.0 (Linux; Android 15; 22081212C Build/AQ3A.241006.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/138.0.7204.63 Mobile Safari/537.36 XWEB/1380045 MMWEBSDK/20250503 MMWEBID/4461 MicroMessenger/8.0.61.2880(0x28003D50) WeChat/arm64 Weixin NetType/5G Language/zh_CN ABI/arm64 MiniProgramEnv/android\",\n" +
                "\t\"x-wx-device-uv-7d\": \"0\"\n" +
                "}";


        Map  jsonObject1 = JSON.parseObject(json1);
        JSONObject  jsonObject2 = JSON.parseObject(json2);

        jsonObject1.forEach((key, value) -> {
            if(!jsonObject2.containsKey(key)){
                System.out.println(key);
            }
        });
    }
}
