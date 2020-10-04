package cn.jianbin.amqplearning.receiver;

import cn.jianbin.amqplearning.entity.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author jianbin
 * @date 2020/10/4 19:04
 */
@Component
public class OrderReceiver {

    /**
     *  如果rabbit server没有这些 exchange queue，会自动创建.
     * @param order
     * @param headers
     * @param channel
     * @throws Exception
     */
    @RabbitHandler
    @RabbitListener(
            bindings = @QueueBinding(exchange =
            @Exchange(value = "order-exchange", durable = "true", type = "direct"),
            value = @Queue(value = "order-queue-3", durable = "true"),
            key = {"order-routing-1", "order-routing-2"})
    )
    public void receive(@Payload Order order, @Headers Map<String, Object> headers,
                        Channel channel) throws Exception {
        System.out.println("================================================================");
        System.out.println(Thread.currentThread().getName());
        System.out.println("received msg: " + order);
        System.out.println("received headers: " + headers);
        System.out.println("================================================================");


        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(tag, false);
    }
}
