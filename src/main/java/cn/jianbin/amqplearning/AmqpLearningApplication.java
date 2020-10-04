package cn.jianbin.amqplearning;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.jianbin.amqplearning.mapper")
public class AmqpLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmqpLearningApplication.class, args);
    }

}
