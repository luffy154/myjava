package com.report.test.dubbo.consumer;

import com.report.test.dubbo.service.PretectServiceInterface;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @ClassName:ProductConsumer
 * @author: qm
 * @Description:
 * @date:2025-07-21
 */
@Service
public class ProductConsumer {
    @DubboReference(loadbalance = "minweight")
    private PretectServiceInterface pretectService;

    public String getProduct() {
        return pretectService.sayHello("dubbo");
    }
}
