package cn.jianbin.amqplearning.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author jianbin
 * @date 2020/10/4 17:22
 */
@Data
@TableName("t_order")
@NoArgsConstructor
public class Order implements Serializable {

    private String id;
    @TableField("order_name")
    private String name;
    private String msgId;
    private BigDecimal totalAmount;
    private Integer state;
    private LocalDateTime createdTime;
}
