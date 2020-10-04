package cn.jianbin.amqplearning.mapper;

import cn.jianbin.amqplearning.entity.OrderMsg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jianbin
 * @since 2020-10-04
 */
@Repository
public interface OrderMsgMapper extends BaseMapper<OrderMsg> {

}
