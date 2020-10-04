package cn.jianbin.amqplearning.service;


import cn.jianbin.amqplearning.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jianbin
 * @since 2020-10-04
 */
public interface IOrderService extends IService<Order> {

    Order createOrder(Order order, String routingKey, String exchange);
}
