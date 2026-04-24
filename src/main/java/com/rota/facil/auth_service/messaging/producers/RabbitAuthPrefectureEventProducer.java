package com.rota.facil.auth_service.messaging.producers;

import com.rota.facil.auth_service.domain.enums.ActionType;
import com.rota.facil.auth_service.http.dto.request.user.CurrentUser;
import com.rota.facil.auth_service.messaging.dto.send.PrefectureEventSend;
import com.rota.facil.auth_service.messaging.mappers.PrefectureEventMapper;
import com.rota.facil.auth_service.persistence.entities.PrefectureEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitAuthPrefectureEventProducer {
    private final RabbitTemplate rabbitTemplate;
    private final PrefectureEventMapper prefectureEventMapper;

    @Value("${rabbitmq.auth.exchange}")
    private String authExchange;

    @Value("${rabbitmq.prefecture.created.routing.key}")
    private String prefectureCreatedRoutingKey;

    @Value("${rabbitmq.prefecture.updated.routing.key}")
    private String prefectureUpdatedRoutingKey;

    @Value("${rabbitmq.prefecture.deleted.routing.key}")
    private String prefectureDeletedRoutingKey;

    public void createPrefectureEvent(PrefectureEntity prefectureCreated, CurrentUser currentUser) {
        PrefectureEventSend prefectureEventSend = prefectureEventMapper.map(prefectureCreated, currentUser, ActionType.CREATE);
        rabbitTemplate.convertAndSend(authExchange, prefectureCreatedRoutingKey, prefectureEventSend);
    }

    public void updatePrefectureEvent(PrefectureEntity prefectureUpdated, CurrentUser currentUser) {
        PrefectureEventSend prefectureEventSend = prefectureEventMapper.map(prefectureUpdated, currentUser, ActionType.UPDATE);
        rabbitTemplate.convertAndSend(authExchange, prefectureUpdatedRoutingKey, prefectureEventSend);
    }

    public void deletePrefectureEvent(PrefectureEntity prefectureDeleted, CurrentUser currentUser) {
        PrefectureEventSend prefectureEventSend = prefectureEventMapper.map(prefectureDeleted, currentUser, ActionType.DELETE);
        rabbitTemplate.convertAndSend(authExchange, prefectureDeletedRoutingKey, prefectureEventSend);
    }

}
