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

    @Value("${rabbitmq.user.trips.increased.routing.key}")
    private String userTripsIncreasedRoutingKey;

    @Value("${rabbitmq.user.trips.decreased.routing.key}")
    private String userTripsDecreasedRoutingKey;

    @Value("${rabbitmq.auth.user.updated.queue}")
    private String userUpdateQueue;

    @Value("${rabbitmq.auth.user.complete.trip.queue}")
    private String userCompleteTripQueue;

    @Value("${rabbitmq.auth.user.trips.increased.queue}")
    private String userTripsIncreasedQueue;

    @Value("${rabbitmq.auth.user.trips.decreased.queue}")
    private String userTripsDecreasedQueue;

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
    public Queue userTripsIncreasedQueue() {
        return new Queue(userTripsIncreasedQueue);
    }

    @Bean
    public Queue userTripsDecreasedQueue() {
        return new Queue(userTripsDecreasedQueue);
    }

    @Bean
    public Binding userFeedbackBinding() {
        return BindingBuilder.bind(this.userUpdateQueue()).to(this.transportExchange()).with(userFeedbackRoutingKey);
    }

    @Bean
    public Binding userCompletedTripBinding() {
        return BindingBuilder.bind(this.userCompleteTripQueue()).to(this.transportExchange()).with(userCompletedTripRoutingKey);
    }

    @Bean
    public Binding userTripsIncreasedBinding() {
        return BindingBuilder.bind(this.userTripsIncreasedQueue()).to(this.transportExchange()).with(userTripsIncreasedRoutingKey);
    }

    @Bean
    public Binding userTripsDecreasedBinding() {
        return BindingBuilder.bind(this.userTripsDecreasedQueue()).to(this.transportExchange()).with(userTripsDecreasedRoutingKey);
    }
}
