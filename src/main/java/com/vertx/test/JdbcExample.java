package com.vertx.test;

import com.alibaba.fastjson2.JSON;
import io.vertx.core.json.JsonObject;
import io.vertx.core.Vertx;
import io.vertx.ext.jdbc.JDBCClient;

/**
 * @ClassName:JdbcExample
 * @author: qm
 * @Description:数据库操作
 * @date:2025-07-09
 */
public class JdbcExample {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        // 配置数据库连接
        JsonObject config = new JsonObject()
                .put("url", "jdbc:mysql://10.10.0.14:3306/db_settlement?useUnicode=true&characterEncoding=utf8&useSSL=false&rewriteBatchedStatements=true&serverTimezone=Asia/Shanghai")
                .put("driver_class", "com.mysql.cj.jdbc.Driver")
                .put("user", "service_wr")
                .put("password", "Wre@d12$kaf8")
                .put("max_pool_size", 30);

        // 创建JDBC客户端
        JDBCClient jdbcClient = JDBCClient.createShared(vertx, config);

        // 执行查询
        jdbcClient.query("SELECT * FROM constant_mapping", res -> {
            if (res.succeeded()) {
                // 处理查询结果
                res.result().getRows().forEach(row -> {
                    System.out.println(JSON.toJSONString( row));
                });
            } else {
                // 处理错误
                res.cause().printStackTrace();
            }
        });
    }
}
