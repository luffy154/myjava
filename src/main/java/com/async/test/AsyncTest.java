package com.async.test;

import java.util.concurrent.CompletableFuture;

/**
 * @ClassName:AsyncTest
 * @author: qm
 * @Description:
 * @date:2025-02-26
 */
public class AsyncTest {
    public static void main(String[] args) {
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(()->{
            throw new RuntimeException("异步任务1发生异常");
        });

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(()->{
            throw new RuntimeException("异步任务2发生异常");
        });


        CompletableFuture.allOf(future1,future2).whenComplete((v,t)->{
            System.out.println("所有任务执行完成..");
        }).thenRun(()->{
            System.out.println("所有任务执行完成...........");
        });
    }
}
