package com.chen.controller;

import com.alibaba.fastjson.JSON;
import com.chen.domain.Order;
import com.chen.domain.Product;
import com.chen.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

//@RestController
@Slf4j
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    /*@Autowired
    private OrderService orderService;*/

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private ProductService productService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @RequestMapping("/order/prod/{pid}")
    public Order order(@PathVariable("pid") Integer pid) {
        log.info("Receiving customer orders and calling the product microservices and query products", pid);

        //从nacos中获取服务地址
        /*List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
        int index = new Random().nextInt(instances.size());//0 1 2

        ServiceInstance serviceInstance =
        discoveryClient.getInstances("service-product").get(index);
        String url = serviceInstance.getHost() + ":" +
                serviceInstance.getPort(); log.info(">>从nacos中获取到的微服务地址为:" + url);*/
        /*String url = "service-product";

        //通过restTemplate调用商品微服务
        Product product = restTemplate.getForObject(
                "http://" + url + "/product/" + pid, Product.class);*/
        //通过fegin调用商品微服务
        Product product = productService.findByPid(pid);
       /* if (product.getPid() == -100) {
            Order order = new Order();
            order.setPname("下单失败");
            return order;
        }*/
        /*log.info(">>商品信息,查询结果:" + JSON.toJSONString(product));*/


        if (product.getPid() == -100) {
            Order order = new Order();
            order.setOid(-100L);
            order.setPname("下单失败");
            return order;
        }

        log.info("查询到{}号商品的信息,内容是:{}", pid, JSON.toJSONString(product));

        //下单(创建订单)
        Order order = new Order();
        order.setUid(1);
        order.setUsername("Test User");
        order.setPid(pid);
        order.setPname(product.getPname());
        order.setPprice(product.getPprice());
        order.setNumber(1);

        //orderService.createOrder(order);
        rocketMQTemplate.convertAndSend("order-topic", order);

        log.info("创建订单成功,订单信息为{}", JSON.toJSONString(order));

        //向mq中投递一个下单成功的消息
        //参数一: 指定topic
        //参数二: 指定消息体
        /*rocketMQTemplate.convertAndSend("order-topic", order);*/
        return order;
    }
}
