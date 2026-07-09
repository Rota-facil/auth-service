package com.rota.facil.auth_service.messaging.producers;

import com.rota.facil.auth_service.domain.enums.PrefectureAuditAction;
import com.rota.facil.auth_service.domain.enums.ResourceName;
import com.rota.facil.auth_service.http.dto.request.user.CurrentUser;
import com.rota.facil.auth_service.messaging.dto.send.prefecture.PrefectureCreatedEventSend;
import com.rota.facil.auth_service.messaging.dto.send.prefecture.PrefectureDeletedEventSend;
import com.rota.facil.auth_service.messaging.dto.send.prefecture.PrefectureUpdatedEventSend;
import com.rota.facil.auth_service.persistence.entities.PrefectureEntity;
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

    public void createPrefectureEvent(PrefectureEntity prefectureCreated, CurrentUser currentUser) {
        PrefectureAuditAction auditAction = PrefectureAuditAction.PREFECTURE_CREATED;
        PrefectureCreatedEventSend eventSend = new PrefectureCreatedEventSend(
                currentUser.userId(),
                currentUser.email(),
                currentUser.role(),
                auditAction.title(currentUser.email(), prefectureCreated.getName()),
                auditAction.getActionType(),
                ResourceName.PREFECTURE.name(),
                prefectureCreated.getId(),
                prefectureCreated.getId(),
                prefectureCreated.getName()
        );

        rabbitTemplate.convertAndSend(authExchange, prefectureCreatedRoutingKey, eventSend);
    }

    public void updatePrefectureEvent(PrefectureEntity prefectureUpdated, CurrentUser currentUser) {
        PrefectureAuditAction auditAction = PrefectureAuditAction.PREFECTURE_UPDATED;
        PrefectureUpdatedEventSend eventSend = new PrefectureUpdatedEventSend(
                currentUser.userId(),
                currentUser.email(),
                currentUser.role(),
                auditAction.title(currentUser.email(), prefectureUpdated.getName()),
                auditAction.getActionType(),
                ResourceName.PREFECTURE.name(),
                prefectureUpdated.getId(),
                prefectureUpdated.getId(),
                prefectureUpdated.getName()
        );

        rabbitTemplate.convertAndSend(authExchange, prefectureUpdatedRoutingKey, eventSend);
    }

    public void deletePrefectureEvent(PrefectureEntity prefectureDeleted, CurrentUser currentUser) {
        PrefectureAuditAction auditAction = PrefectureAuditAction.PREFECTURE_DELETED;
        PrefectureDeletedEventSend eventSend = new PrefectureDeletedEventSend(
                currentUser.userId(),
                currentUser.email(),
                currentUser.role(),
                auditAction.title(currentUser.email(), prefectureDeleted.getName()),
                auditAction.getActionType(),
                ResourceName.PREFECTURE.name(),
                prefectureDeleted.getId(),
                prefectureDeleted.getId(),
                prefectureDeleted.getName()
        );

        rabbitTemplate.convertAndSend(authExchange, prefectureDeletedRoutingKey, eventSend);
    }
}
