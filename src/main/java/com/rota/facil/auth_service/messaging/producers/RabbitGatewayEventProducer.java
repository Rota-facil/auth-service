package com.rota.facil.auth_service.messaging.producers;

import com.rota.facil.auth_service.messaging.dto.send.GatewayUserDeletedEventSend;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitGatewayEventProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.auth.exchange}")
    private String authExchange;

    @Value("${rabbitmq.file.user.deleted.routing.key}")
    private String userDeletedRoutingKey;

    public void deleteUserEvent(GatewayUserDeletedEventSend gatewayUserEventSend) {
        rabbitTemplate.convertAndSend(authExchange, userDeletedRoutingKey, gatewayUserEventSend);
    }
}
