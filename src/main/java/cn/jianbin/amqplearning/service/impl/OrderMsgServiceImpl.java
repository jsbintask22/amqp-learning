package cn.jianbin.amqplearning.service.impl;

import cn.jianbin.amqplearning.entity.OrderMsg;
import cn.jianbin.amqplearning.mapper.OrderMsgMapper;
import cn.jianbin.amqplearning.service.IOrderMsgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jianbin
 * @since 2020-10-04
 */
@Service
public class OrderMsgServiceImpl extends ServiceImpl<OrderMsgMapper, OrderMsg> implements IOrderMsgService {

}
