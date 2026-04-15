package com.rota.facil.auth_service.messaging.producers;

import com.rota.facil.auth_service.messaging.dto.send.AuditUserEventSend;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitAuditEventProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.auth.exchange}")
    private String authExchange;

    @Value("${rabbitmq.file.user.created.routing.key}")
    private String userCreatedRoutingKey;

    @Value("${rabbitmq.file.user.updated.routing.key}")
    private String userUpdatedRoutingKey;

    @Value("${rabbitmq.file.user.deleted.routing.key}")
    private String userDeletedRoutingKey;

    @Value("${rabbitmq.file.prefecture.created.routing.key}")
    private String prefectureCreatedRoutingKey;

    @Value("${rabbitmq.file.prefecture.updated.routing.key}")
    private String prefectureUpdatedRoutingKey;

    @Value("${rabbitmq.file.prefecture.deleted.routing.key}")
    private String prefectureDeletedRoutingKey;

    public void createUserEvent(AuditUserEventSend auditUserEventSend) {
        rabbitTemplate.convertAndSend(authExchange, userCreatedRoutingKey, auditUserEventSend);
    }

    public void updateUserEvent(AuditUserEventSend auditUserEventSend) {
        rabbitTemplate.convertAndSend(authExchange, userUpdatedRoutingKey, auditUserEventSend);
    }

    public void deleteUserEvent(AuditUserEventSend auditUserEventSend) {
        rabbitTemplate.convertAndSend(authExchange, userDeletedRoutingKey, auditUserEventSend);
    }

    public void createPrefectureEvent(AuditUserEventSend auditUserEventSend) {
        rabbitTemplate.convertAndSend(authExchange, prefectureCreatedRoutingKey, auditUserEventSend);
    }

    public void updatePrefectureEvent(AuditUserEventSend auditUserEventSend) {
        rabbitTemplate.convertAndSend(authExchange, prefectureUpdatedRoutingKey, auditUserEventSend);
    }

    public void deletePrefectureEvent(AuditUserEventSend auditUserEventSend) {
        rabbitTemplate.convertAndSend(authExchange, prefectureDeletedRoutingKey, auditUserEventSend);
    }
}
