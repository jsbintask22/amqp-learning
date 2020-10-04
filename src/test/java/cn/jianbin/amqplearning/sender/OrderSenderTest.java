package cn.jianbin.amqplearning.sender;

import cn.jianbin.amqplearning.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jianbin
 * @date 2020/10/4 17:28
 */
@SpringBootTest
class OrderSenderTest {
    @Autowired
    private OrderSender orderSender;

    @Test
    void send() {

    }
}