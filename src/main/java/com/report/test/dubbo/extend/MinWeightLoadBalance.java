package com.report.test.dubbo.extend;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.SPI;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.LoadBalance;

import java.util.List;

/**
 * @ClassName:MinWeightLoadBalance
 * @author: qm
 * @Description:
 * @date:2025-07-21
 */
@SPI("random")
public class MinWeightLoadBalance implements LoadBalance {
    @Override
    public <T> Invoker<T> select(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        if (invokers == null || invokers.isEmpty()) {
            return null;
        }
        Invoker<T> minInvoker = invokers.get(0);
        int minWeight = minInvoker.getUrl().getParameter("weight", 100);
        for (Invoker<T> invoker : invokers) {
            int weight = invoker.getUrl().getParameter("weight", 100);
            if (weight < minWeight) {
                minWeight = weight;
                minInvoker = invoker;
            }
        }
        return minInvoker;
    }
}
