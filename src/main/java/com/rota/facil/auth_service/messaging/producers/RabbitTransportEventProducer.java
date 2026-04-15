package com.rota.facil.auth_service.messaging.producers;

import com.rota.facil.auth_service.messaging.dto.send.TransportUserCreatedEventSend;
import com.rota.facil.auth_service.messaging.dto.send.TransportUserDeletedEventSend;
import com.rota.facil.auth_service.messaging.dto.send.TransportUserUpdatedEventSend;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitTransportEventProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.auth.exchange}")
    private String authExchange;

    @Value("${rabbitmq.file.user.created.routing.key}")
    private String userCreatedRoutingKey;

    @Value("${rabbitmq.file.user.updated.routing.key}")
    private String userUpdatedRoutingKey;

    @Value("${rabbitmq.file.user.deleted.routing.key}")
    private String userDeletedRoutingKey;


    public void createUserEvent(TransportUserCreatedEventSend transportUserCreatedEventSend) {
        rabbitTemplate.convertAndSend(authExchange, userCreatedRoutingKey, transportUserCreatedEventSend);
    }

    public void updateUserEvent(TransportUserUpdatedEventSend transportUserUpdatedEventSend) {
        rabbitTemplate.convertAndSend(authExchange, userUpdatedRoutingKey, transportUserUpdatedEventSend);
    }

    public void deleteUserEvent(TransportUserDeletedEventSend transportUserDeletedEventSend) {
        rabbitTemplate.convertAndSend(authExchange, userDeletedRoutingKey, transportUserDeletedEventSend);
    }
}
