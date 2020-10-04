package cn.jianbin.amqplearning.controller;

import cn.jianbin.amqplearning.entity.Order;
import cn.jianbin.amqplearning.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author jianbin
 * @date 2020/10/4 19:24
 */
@RestController
@RequestMapping
public class OrderController {


    @Autowired
    private IOrderService orderService;

    @GetMapping("/order")
    public Order purchaseOrder(String key, String exchange) {
        Order order = new Order();
        order.setCreatedTime(LocalDateTime.now());
        order.setId(System.currentTimeMillis() + "order_id");
        order.setMsgId(System.currentTimeMillis() + "msg_id");
        order.setName("order_name->" + System.currentTimeMillis() + Thread.currentThread().getName());
        order.setState(1);
        order.setTotalAmount(new BigDecimal("1000"));

        orderService.createOrder(order, key, exchange);

        return order;
    }
}
