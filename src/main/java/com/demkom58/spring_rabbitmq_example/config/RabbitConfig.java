package com.demkom58.spring_rabbitmq_example.config;

import com.demkom58.spring_rabbitmq_example.config.properties.RabbitProperties;
import com.rabbitmq.client.ConnectionFactory;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import static java.lang.Integer.parseInt;


@Configuration
@AllArgsConstructor
public class RabbitConfig {

    private final RabbitProperties rabbitProperties;
    @Bean
    public CachingConnectionFactory rabbitConnectionFactory() throws KeyManagementException, NoSuchAlgorithmException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(rabbitProperties.getHost());
        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());
        connectionFactory.setPort(parseInt(rabbitProperties.getPort()));
        return new CachingConnectionFactory(connectionFactory);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory()
            throws NoSuchAlgorithmException, KeyManagementException {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(rabbitConnectionFactory());
        factory.setConcurrentConsumers(parseInt(rabbitProperties.getConsumers()));
        factory.setMaxConcurrentConsumers(parseInt(rabbitProperties.getMaxConsumers()));
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setDefaultRequeueRejected(true);
        factory.setErrorHandler(errorHandler());
        return factory;
    }

    @Bean
    public ErrorHandler errorHandler() {
        return new ConditionalRejectingErrorHandler(new ConditionalRejectingErrorHandler.DefaultExceptionStrategy());
    }
}
