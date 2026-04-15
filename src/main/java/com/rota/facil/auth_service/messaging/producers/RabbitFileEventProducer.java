package com.rota.facil.auth_service.messaging.producers;

import com.rota.facil.auth_service.messaging.dto.send.FilePrefectureDeletedEventSend;
import com.rota.facil.auth_service.messaging.dto.send.FileUserDeletedEventSend;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitFileEventProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.auth.exchange}")
    private String authExchange;

    @Value("${rabbitmq.user.deleted.routing.key}")
    private String userDeletedRoutingKey;

    @Value("${rabbitmq.prefecture.deleted.routing.key}")
    private String prefectureDeletedRoutingKey;

    public void deleteUserEvent(FileUserDeletedEventSend fileUserDeletedEventSend) {
        rabbitTemplate.convertAndSend(authExchange, userDeletedRoutingKey, fileUserDeletedEventSend);
    }

    public void deletePrefectureEvent(FilePrefectureDeletedEventSend fileUserEventSend) {
        rabbitTemplate.convertAndSend(authExchange, prefectureDeletedRoutingKey, fileUserEventSend);
    }
}
