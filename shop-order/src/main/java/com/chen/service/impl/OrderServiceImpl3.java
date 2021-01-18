package com.chen.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.chen.domain.Order;
import com.chen.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl3 implements OrderService {
    int i = 0;
    @SentinelResource(
            value = "message",
            blockHandlerClass = OrderServiceImpl3BlockHandler.class,
            blockHandler = "blockHandler",//指定发生BlockException时进入的方法
            fallbackClass = OrderServiceImpl3Fallback.class,
            fallback = "fallback"//指定发生Throwable时进入的方法
    )
    public String message(String name) {
        i++;
        if (i % 3 == 0) {
            throw new RuntimeException();
        }
        return "message";
    }
    @Override
    public void createOrder(Order order) {

    }
}
