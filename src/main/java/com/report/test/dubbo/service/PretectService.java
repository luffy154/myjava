package com.report.test.dubbo.service;

import org.apache.dubbo.config.annotation.DubboService;

/**
 * @ClassName:PretectService
 * @author: qm
 * @Description:
 * @date:2025-07-21
 */
@DubboService
public class PretectService implements PretectServiceInterface{
    public String sayHello(String name) {
        return "Hello " + name + " , this is PretectService";
    }
}
