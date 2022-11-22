package com.demkom58.spring_rabbitmq_example.config.properties;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RabbitProperties {
    private String host = "---";
    private String username = "---";
    private String password = "---";
    private String queue = "QABC.TestQueue";
    private String port = "---";
    private String consumers = "---";
    private String maxConsumers = "---";
    private String sslProtocol = "---";
}
