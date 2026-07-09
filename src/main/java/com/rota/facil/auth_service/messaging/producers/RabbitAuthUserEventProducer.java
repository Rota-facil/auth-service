package com.rota.facil.auth_service.messaging.producers;

import com.rota.facil.auth_service.domain.enums.ResourceName;
import com.rota.facil.auth_service.domain.enums.UserAuditAction;
import com.rota.facil.auth_service.http.dto.request.user.CurrentUser;
import com.rota.facil.auth_service.messaging.dto.send.user.DriverUpdatedByAdminEventSend;
import com.rota.facil.auth_service.messaging.dto.send.user.UserCreatedEventSend;
import com.rota.facil.auth_service.messaging.dto.send.user.UserDeactivatedEventSend;
import com.rota.facil.auth_service.messaging.dto.send.user.UserDeletedEventSend;
import com.rota.facil.auth_service.messaging.dto.send.user.UserEmailChangedEventSend;
import com.rota.facil.auth_service.messaging.dto.send.user.UserLogoutEventSend;
import com.rota.facil.auth_service.messaging.dto.send.user.UserUpdatedEventSend;
import com.rota.facil.auth_service.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

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

    @Value("${rabbitmq.user.logout.routing.key}")
    private String userLogoutRoutingKey;

    @Value("${rabbitmq.driver.admin.updated.routing.key}")
    private String driverAdminUpdatedRoutingKey;

    public void createUserEvent(UserEntity userCreated, UserAuditAction auditAction) {
        UserCreatedEventSend eventSend = new UserCreatedEventSend(
                userCreated.getId(),
                userCreated.getPrefecture().getId(),
                userCreated.getName(),
                userCreated.getEmail(),
                userCreated.getRole(),
                userCreated.getCpf(),
                null,
                userCreated.getActive(),
                null,
                null,
                null,
                auditAction.title(userCreated.getEmail()),
                auditAction.getActionType(),
                ResourceName.USERS.name(),
                userCreated.getId()
        );

        rabbitTemplate.convertAndSend(authExchange, userCreatedRoutingKey, eventSend);
    }

    public void createDriverByAdminEvent(UserEntity driverCreated, CurrentUser admin) {
        UserAuditAction auditAction = UserAuditAction.DRIVER_CREATED_BY_ADMIN;
        UserCreatedEventSend eventSend = new UserCreatedEventSend(
                driverCreated.getId(),
                driverCreated.getPrefecture().getId(),
                driverCreated.getName(),
                driverCreated.getEmail(),
                driverCreated.getRole(),
                driverCreated.getCpf(),
                null,
                driverCreated.getActive(),
                admin.userId(),
                admin.email(),
                admin.role(),
                auditAction.title(admin.email(), driverCreated.getEmail()),
                auditAction.getActionType(),
                ResourceName.USERS.name(),
                driverCreated.getId()
        );

        rabbitTemplate.convertAndSend(authExchange, userCreatedRoutingKey, eventSend);
    }

    public void updateUserEvent(UserEntity userUpdated, UserAuditAction auditAction) {
        UserUpdatedEventSend eventSend = new UserUpdatedEventSend(
                userUpdated.getId(),
                userUpdated.getPrefecture().getId(),
                userUpdated.getName(),
                userUpdated.getEmail(),
                userUpdated.getRole(),
                userUpdated.getCpf(),
                null,
                userUpdated.getActive(),
                null,
                null,
                null,
                auditAction.title(userUpdated.getEmail()),
                auditAction.getActionType(),
                ResourceName.USERS.name(),
                userUpdated.getId()
        );

        rabbitTemplate.convertAndSend(authExchange, userUpdatedRoutingKey, eventSend);
    }

    public void updateDriverByAdminEvent(UserEntity driverUpdated, CurrentUser admin) {
        UserAuditAction auditAction = UserAuditAction.DRIVER_UPDATED_BY_ADMIN;
        DriverUpdatedByAdminEventSend eventSend = new DriverUpdatedByAdminEventSend(
                driverUpdated.getId(),
                driverUpdated.getPrefecture().getId(),
                driverUpdated.getName(),
                driverUpdated.getEmail(),
                driverUpdated.getRole(),
                driverUpdated.getCpf(),
                null,
                driverUpdated.getActive(),
                admin.userId(),
                admin.email(),
                admin.role(),
                auditAction.title(admin.email(), driverUpdated.getEmail()),
                auditAction.getActionType(),
                ResourceName.USERS.name(),
                driverUpdated.getId()
        );

        rabbitTemplate.convertAndSend(authExchange, driverAdminUpdatedRoutingKey, eventSend);
    }

    public void emailChangedUserEvent(UserEntity userEmailChanged, String token) {
        UserAuditAction auditAction = UserAuditAction.USER_EMAIL_CHANGED;
        UserEmailChangedEventSend eventSend = new UserEmailChangedEventSend(
                userEmailChanged.getId(),
                userEmailChanged.getPrefecture().getId(),
                userEmailChanged.getName(),
                userEmailChanged.getEmail(),
                userEmailChanged.getRole(),
                userEmailChanged.getCpf(),
                token,
                userEmailChanged.getActive(),
                null,
                null,
                null,
                auditAction.title(userEmailChanged.getEmail()),
                auditAction.getActionType(),
                ResourceName.USERS.name(),
                userEmailChanged.getId()
        );

        rabbitTemplate.convertAndSend(authExchange, userEmailChangedRoutingKey, eventSend);
    }

    public void deleteUserEvent(UserEntity userDeleted, String token) {
        UserAuditAction auditAction = UserAuditAction.USER_DELETED_OWN_ACCOUNT;
        UserDeletedEventSend eventSend = new UserDeletedEventSend(
                userDeleted.getId(),
                userDeleted.getPrefecture().getId(),
                userDeleted.getName(),
                userDeleted.getEmail(),
                userDeleted.getRole(),
                userDeleted.getCpf(),
                token,
                userDeleted.getActive(),
                null,
                null,
                null,
                auditAction.title(userDeleted.getEmail()),
                auditAction.getActionType(),
                ResourceName.USERS.name(),
                userDeleted.getId()
        );

        rabbitTemplate.convertAndSend(authExchange, userDeletedRoutingKey, eventSend);
    }

    public void deactivateUserEvent(UserEntity userDeactivated, String token) {
        UserAuditAction auditAction = UserAuditAction.USER_DEACTIVATED_OWN_ACCOUNT;
        UserDeactivatedEventSend eventSend = new UserDeactivatedEventSend(
                userDeactivated.getId(),
                userDeactivated.getPrefecture().getId(),
                userDeactivated.getName(),
                userDeactivated.getEmail(),
                userDeactivated.getRole(),
                userDeactivated.getCpf(),
                token,
                userDeactivated.getActive(),
                null,
                null,
                null,
                auditAction.title(userDeactivated.getEmail()),
                auditAction.getActionType(),
                ResourceName.USERS.name(),
                userDeactivated.getId()
        );

        rabbitTemplate.convertAndSend(authExchange, userDeactivateRoutingKey, eventSend);
    }

    public void adminDeactivateUserEvent(UserEntity driverDeactivated, CurrentUser admin) {
        UserAuditAction auditAction = UserAuditAction.DRIVER_DEACTIVATED_BY_ADMIN;
        UserDeactivatedEventSend eventSend = new UserDeactivatedEventSend(
                driverDeactivated.getId(),
                driverDeactivated.getPrefecture().getId(),
                driverDeactivated.getName(),
                driverDeactivated.getEmail(),
                driverDeactivated.getRole(),
                driverDeactivated.getCpf(),
                null,
                driverDeactivated.getActive(),
                admin.userId(),
                admin.email(),
                admin.role(),
                auditAction.title(admin.email(), driverDeactivated.getEmail()),
                auditAction.getActionType(),
                ResourceName.USERS.name(),
                driverDeactivated.getId()
        );

        rabbitTemplate.convertAndSend(authExchange, userDeactivateRoutingKey, eventSend);
    }

    public void logout(UserEntity userLogout, String token) {
        UserAuditAction auditAction = UserAuditAction.USER_LOGOUT;
        UserLogoutEventSend eventSend = new UserLogoutEventSend(
                userLogout.getId(),
                userLogout.getPrefecture().getId(),
                userLogout.getName(),
                userLogout.getEmail(),
                userLogout.getRole(),
                userLogout.getCpf(),
                token,
                userLogout.getActive(),
                null,
                null,
                null,
                auditAction.title(userLogout.getEmail()),
                auditAction.getActionType(),
                ResourceName.USERS.name(),
                userLogout.getId()
        );

        rabbitTemplate.convertAndSend(authExchange, userLogoutRoutingKey, eventSend);
    }
}
