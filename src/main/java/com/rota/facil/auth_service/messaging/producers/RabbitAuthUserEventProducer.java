package com.rota.facil.auth_service.messaging.producers;

import com.rota.facil.auth_service.domain.enums.UserActionType;
import com.rota.facil.auth_service.messaging.dto.send.user.UserEventSend;
import com.rota.facil.auth_service.messaging.mappers.UserEventMapper;
import com.rota.facil.auth_service.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitAuthUserEventProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.auth.exchange}")
    private String authExchange;

    @Value("${rabbitmq.user.created.routing.key}")
    private String userCreatedRoutingKey;

    @Value("${rabbitmq.user.updated.routing.key}")
    private String userUpdatedRoutingKey;

    @Value("${rabbitmq.user.deleted.routing.key}")
    private String userDeletedRoutingKey;

    @Value("${rabbitmq.user.email.changed.routing.key}")
    private String userEmailChangedRoutingKey;

    @Value("${rabbitmq.user.deactivate.routing.key}")
    private String userDeactivateRoutingKey;

    private final UserEventMapper userEventMapper;

    public void createUserEvent(UserEntity userCreated) {
        UserEventSend userEventSend = userEventMapper.map(userCreated, UserActionType.CREATE, userCreated.getId());
        rabbitTemplate.convertAndSend(authExchange, userCreatedRoutingKey, userEventSend);
    }

    public void updateUserEvent(UserEntity userUpdated) {
        UserEventSend userEventSend = userEventMapper.map(userUpdated, UserActionType.UPDATE, userUpdated.getId());
        rabbitTemplate.convertAndSend(authExchange, userUpdatedRoutingKey, userEventSend);
    }

    public void emailChangedUserEvent(UserEntity userEmailChanged, String token) {
        UserEventSend userEventSend = userEventMapper.map(userEmailChanged, token, UserActionType.UPDATE, userEmailChanged.getId());
        rabbitTemplate.convertAndSend(authExchange, userEmailChangedRoutingKey, userEventSend);
    }

    public void deleteUserEvent(UserEntity userDeleted, String token) {
        UserEventSend userEventSend = userEventMapper.map(userDeleted, token, UserActionType.DELETE, userDeleted.getId());
        rabbitTemplate.convertAndSend(authExchange, userDeletedRoutingKey, userEventSend);
    }

    public void deactivateUserEvent(UserEntity userDeactivated, String token) {
        UserEventSend userEventSend = userEventMapper.map(userDeactivated, token, UserActionType.DEACTIVATE, userDeactivated.getId());
        rabbitTemplate.convertAndSend(authExchange, userDeactivateRoutingKey, userEventSend);
    }
}
