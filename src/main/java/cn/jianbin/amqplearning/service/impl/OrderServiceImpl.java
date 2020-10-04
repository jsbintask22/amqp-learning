package cn.jianbin.amqplearning.service.impl;

import cn.jianbin.amqplearning.entity.Order;
import cn.jianbin.amqplearning.entity.OrderMsg;
import cn.jianbin.amqplearning.mapper.OrderMapper;
import cn.jianbin.amqplearning.mapper.OrderMsgMapper;
import cn.jianbin.amqplearning.sender.OrderSender;
import cn.jianbin.amqplearning.service.IOrderService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbitmq.tools.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jianbin
 * @since 2020-10-04
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private OrderMsgMapper orderMsgMapper;

    @Autowired
    private OrderSender orderSender;

    @Override
    public Order createOrder(Order order, String routingKey, String exchange) {
        // 入库
        baseMapper.insert(order);
        // 插入消息表
        OrderMsg orderMsg = new OrderMsg();
        orderMsg.setBody(JSON.toJSONString(order, true));
        orderMsg.setMsgId(order.getMsgId());
        orderMsg.setCreatedTime(LocalDateTime.now());
        // 超时时间设置为  30s
        orderMsg.setTimeoutTime(LocalDateTime.now().plusSeconds(30));
        orderMsg.setRetryCount(0);
        // 0 待投递  1 - 投递成功   2 - 重试3次后依然投递失败    3 - 消息消费成功
        orderMsg.setState(0);
        orderMsg.setRoutingKey(routingKey);
        orderMsg.setExchange(exchange);

        orderMsgMapper.insert(orderMsg);
        System.err.println("保存订单，订单消息成功");

        orderSender.send(order, routingKey, exchange);

        return order;
    }
}
