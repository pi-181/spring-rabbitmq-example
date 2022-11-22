package com.demkom58.spring_rabbitmq_example.controller;

import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

import static org.springframework.amqp.support.AmqpHeaders.DELIVERY_TAG;

@Component
@AllArgsConstructor
@Slf4j
public class RabbitConsumer {
    @RabbitListener(queues = "#{rabbitProperties.queue}",  id = "testId")
    public void process(Message message, Channel channel, @Header(DELIVERY_TAG) long tag) {
        String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("Got new message from queue: {}", messageBody);
        try {
            if(messageBody.equals("Hello from Lugovskyi")) {
                channel.basicAck(tag, false);
                log.info("greeting from Lugovskyi acked");
            } else {
                channel.basicNack(tag, false, false);
                log.info("not valid message nacked");
            }
        } catch (Exception e) {
            log.error("Catching {} during processing RabbitMQ message. Will send message to a dead queue", e.getClass().getSimpleName(), e);
        }
    }
}
