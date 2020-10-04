package cn.jianbin.amqplearning.sender;

import cn.jianbin.amqplearning.entity.Order;
import cn.jianbin.amqplearning.entity.OrderMsg;
import cn.jianbin.amqplearning.mapper.OrderMsgMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author jianbin
 * @date 2020/10/4 17:22
 */
@Component
public class OrderSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private OrderMsgMapper orderMsgMapper;

    final RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) -> {
        System.err.println(Thread.currentThread().getName() + "收到rabbit回调：" + correlationData +
                " " + ack +
                " " + cause);

        if (ack) {
            String msgId = correlationData.getId();
            OrderMsg orderMsg = orderMsgMapper.selectOne(Wrappers.lambdaQuery(OrderMsg.class).eq(OrderMsg::getMsgId,
                    msgId));
            orderMsg.setState(1);

            orderMsgMapper.updateById(orderMsg);
        } else {
            System.err.println("ack failed. cause :" + cause);
        }
    };

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(confirmCallback);
    }

    public void send(Order order) {
        CorrelationData correlationData = new CorrelationData(order.getMsgId());
        rabbitTemplate.convertAndSend("order-exchange",
                "order-routing",
                order,
                correlationData);

        System.out.println(correlationData.getReturnedMessage());
    }


    public void send(Order order, String key) {
        CorrelationData correlationData = new CorrelationData(order.getMsgId());
        rabbitTemplate.convertAndSend("order-exchange",
                key,
                order,
                correlationData);
    }

    public void send(Order order, String key, String exchange) {
        CorrelationData correlationData = new CorrelationData(order.getMsgId());
        rabbitTemplate.convertAndSend(exchange,
                key,
                order,
                correlationData);
    }
}
