package com.demkom58.spring_rabbitmq_example;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableRabbit
@EnableAsync
public class SpringRabbitmqExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringRabbitmqExampleApplication.class, args);
    }
}