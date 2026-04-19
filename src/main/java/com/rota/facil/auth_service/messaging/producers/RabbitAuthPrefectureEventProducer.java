package com.rota.facil.auth_service.messaging.producers;

import com.rota.facil.auth_service.messaging.dto.send.PrefectureEventSend;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitAuthPrefectureEventProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.auth.exchange}")
    private String authExchange;

    @Value("${rabbitmq.prefecture.created.routing.key}")
    private String prefectureCreatedRoutingKey;

    @Value("${rabbitmq.prefecture.updated.routing.key}")
    private String prefectureUpdatedRoutingKey;

    @Value("${rabbitmq.prefecture.deleted.routing.key}")
    private String prefectureDeletedRoutingKey;

    public void createPrefectureEvent(PrefectureEventSend prefectureCreated) {
        rabbitTemplate.convertAndSend(authExchange, prefectureCreatedRoutingKey, prefectureCreated);
    }

    public void updatePrefectureEvent(PrefectureEventSend prefectureUpdated) {
        rabbitTemplate.convertAndSend(authExchange, prefectureUpdatedRoutingKey, prefectureUpdated);
    }

    public void deletePrefectureEvent(PrefectureEventSend prefectureDeleted) {
        rabbitTemplate.convertAndSend(authExchange, prefectureDeletedRoutingKey, prefectureDeleted);
    }

}
