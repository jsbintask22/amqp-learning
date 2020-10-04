package cn.jianbin.amqplearning.task;

import cn.jianbin.amqplearning.entity.Order;
import cn.jianbin.amqplearning.entity.OrderMsg;
import cn.jianbin.amqplearning.mapper.OrderMsgMapper;
import cn.jianbin.amqplearning.sender.OrderSender;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jianbin
 * @date 2020/10/4 21:06
 */
@Component
public class OrderScheduler {
    @Autowired
    private OrderSender orderSender;

    @Autowired
    private OrderMsgMapper orderMsgMapper;

    private AtomicInteger count = new AtomicInteger(1);

    private ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(
            4, r -> new Thread(r, "order_msg_state_query_" + count.getAndIncrement())
    );

    @PostConstruct
    public void initTask() {
        System.err.println("init task.");
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.err.println("开始执行定时消息状态检查，" + Thread.currentThread().getName() + " " + LocalDateTime.now());

            // 找到所有 消息状态为 待确认  且已经过了超时时间的 消息，开始重发消息
            LambdaQueryWrapper<OrderMsg> wrapper = Wrappers.lambdaQuery(OrderMsg.class).eq(OrderMsg::getState, 0)
                    .lt(OrderMsg::getTimeoutTime, LocalDateTime.now());

            List<OrderMsg> orderMsgs = orderMsgMapper.selectList(wrapper);
            System.err.println("找到" + orderMsgs.size() + "个待确认消息;");
            orderMsgs.forEach(msg -> {
                try {
                    if (msg.getRetryCount() > 2) {
                        // 重试 3 次了， 还没确认， 本次投递失败；
                        msg.setState(2);
                        System.err.println("消息确认失败： " + msg);
                    } else {
                        msg.setRetryCount(msg.getRetryCount() + 1);
                        Order order = JSON.parseObject(msg.getBody(), Order.class);
                        // 重发
                        orderSender.send(order, msg.getRoutingKey(), msg.getExchange());
                    }

                    orderMsgMapper.updateById(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }, 5000L, 5000L, TimeUnit.MILLISECONDS);
    }

}
