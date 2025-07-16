package com.report.test;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.gson.JsonParser;
import com.lark.oapi.Client;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.service.task.v2.model.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @ClassName:BiTableTest
 * @author: qm
 * @Description:
 * @date:2025-06-20
 */
public class BiTableTest {
    public static void main(String[] args) throws Exception {
//        JSONObject params = new JSONObject();
//        params.put("app_secret", "m0u6En30gDBdS2QyHhiq0fjeJPyM3r0H");
//        params.put("app_id", "cli_a3da638c30b2900e");
//        String url = "https://open.feishu.cn/open-apis/auth/v3/app_access_token/internal";
//        String dataJson = HttpUtil.post(url, params, 30000);
//        AccessToken accessToken = JSONObject.parseObject(dataJson, AccessToken.class);
//        System.out.println(dataJson);
//
//        if (accessToken.hasSuccess()) {
//
//        } else {
//            System.out.println(dataJson);
//        }
        String token = "t-g1046kfnTYMFUP455P5UBBAVHD4APJH47XQTWRF4";

//        String taskUrl="https://open.feishu.cn/open-apis/task/v2/tasks?user_id_type=open_id";
//        JSONObject taskParams = new JSONObject();
//        taskParams.put("summary", "上传文件测试任务");
//
//        taskParams.put("summary", "上传文件测试任务");
//        HttpRequest taskRequest = HttpRequest.post(taskUrl)
//                .header("Authorization", "Bearer " + token)
//                .header("Content-Type","application/json; charset=utf-8")
//                .body(taskParams.toJSONString());
//        HttpResponse taskResp =taskRequest.execute();
//        System.out.println(taskResp.body());
//        JSONObject taskInfo = JSON.parseObject(taskResp.body());

        Path filePath =  Paths.get("test.pdf");
        byte[] bytes = Files.readAllBytes(filePath);
//        String uploadUrl ="https://open.feishu.cn/open-apis/task/v2/attachments/upload?user_id_type=open_id";
//        HttpRequest request = HttpRequest.post(uploadUrl)
//                .header("Authorization", "Bearer " + token)
//                .header("Content-Type","multipart/form-data")
//                .form("resource_type", "task")
//                .form("resource_id", taskInfo.getJSONObject("data").getJSONObject("task").get("guid"))
//                .form("file", bytes, "test.pdf");
//
//        HttpResponse resp =request.execute();
//        System.out.println(resp.body());


        String uploadAllUrl = "https://open.feishu.cn/open-apis/drive/v1/files/upload_all";
        HttpRequest uploadRequest = HttpRequest.post(uploadAllUrl)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type","multipart/form-data")
                .form("file_name", "test.pdf")
                .form("parent_type", "bitable")
                .form("parent_node", "ZXhZbFgCDawpgqsoTGOcVkEZnfd")
                .form("size",""+bytes.length)
                .form("file", bytes, "test.pdf");
        HttpResponse resp =uploadRequest.execute();
        System.out.println(resp.body());
    }
}
