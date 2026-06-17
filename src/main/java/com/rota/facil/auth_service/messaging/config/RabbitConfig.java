package com.rota.facil.auth_service.messaging.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Value("${rabbitmq.auth.exchange}")
    private String authExchange;

    @Value("${rabbitmq.transport.exchange}")
    private String transportExchange;

    @Value("${rabbitmq.user.feedback.routing.key}")
    private String userFeedbackRoutingKey;

    @Value("${rabbitmq.user.trip.completed.routing.key}")
    private String userCompletedTripRoutingKey;

    @Value("${rabbitmq.auth.user.updated.queue}")
    private String userUpdateQueue;

    @Value("${rabbitmq.auth.user.complete.trip.queue}")
    private String userCompleteTripQueue;

    @Bean
    public Jackson2JsonMessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitListener(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter
    ) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter
    ) {
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
        simpleRabbitListenerContainerFactory.setMessageConverter(messageConverter);
        return simpleRabbitListenerContainerFactory;
    }

    @Bean
    public TopicExchange authExchange() {
        return new TopicExchange(authExchange);
    }

    @Bean
    public TopicExchange transportExchange() {
        return new TopicExchange(transportExchange);
    }

    @Bean
    public Queue userUpdateQueue() {
        return new Queue(userUpdateQueue);
    }

    @Bean
    public Queue userCompleteTripQueue() {
        return new Queue(userCompleteTripQueue);
    }

    @Bean
    public Binding userFeedbackBinding() {
        return BindingBuilder.bind(this.userUpdateQueue()).to(this.transportExchange()).with(userFeedbackRoutingKey);
    }

    @Bean
    public Binding userCompletedTripBinding() {
        return BindingBuilder.bind(this.userCompleteTripQueue()).to(this.transportExchange()).with(userCompletedTripRoutingKey);
    }
}
