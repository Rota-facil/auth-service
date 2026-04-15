package com.rota.facil.auth_service.messaging.producers;

import com.rota.facil.auth_service.messaging.dto.send.GatewayUserDeletedEventSend;
import com.rota.facil.auth_service.messaging.dto.send.GatewayUserUpdatedEventSend;
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

    @Value("${rabbitmq.user.deleted.routing.key}")
    private String userDeletedRoutingKey;

    @Value("${rabbitmq.user.updated.routing.key}")
    private String userUpdatedRoutingKey;

    public void deleteUserEvent(GatewayUserDeletedEventSend gatewayUserEventSend) {
        rabbitTemplate.convertAndSend(authExchange, userDeletedRoutingKey, gatewayUserEventSend);
    }

    public void updateUserEvent(GatewayUserUpdatedEventSend gatewayUserUpdatedEventSend) {
        rabbitTemplate.convertAndSend(authExchange, userUpdatedRoutingKey, gatewayUserUpdatedEventSend);
    }
}
