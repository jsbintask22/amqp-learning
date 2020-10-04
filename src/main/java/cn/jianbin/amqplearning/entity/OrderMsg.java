package cn.jianbin.amqplearning.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author jianbin
 * @since 2020-10-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderMsg implements Serializable {
    private Integer id;

    private String msgId;

    private String body;

    private Integer state;

    private LocalDateTime timeoutTime;

    private Integer retryCount;

    private LocalDateTime createdTime;


    private String routingKey;
    private String exchange;

}
