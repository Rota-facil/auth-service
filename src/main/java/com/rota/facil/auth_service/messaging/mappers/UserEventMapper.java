package com.rota.facil.auth_service.messaging.mappers;

import com.rota.facil.auth_service.domain.enums.ActionType;
import com.rota.facil.auth_service.domain.enums.ResourceName;
import com.rota.facil.auth_service.http.dto.request.user.CurrentUser;
import com.rota.facil.auth_service.messaging.dto.send.user.DriverUpdatedByAdminAuditEventSend;
import com.rota.facil.auth_service.messaging.dto.send.user.UserEventSend;
import com.rota.facil.auth_service.persistence.entities.UserEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserEventMapper {

    @Mapping(target = "userId", source = "entity.id")
    @Mapping(target = "prefectureId", source = "entity.prefecture.id")
    @Mapping(target = "resourceName", expression = "java(ResourceName.USERS)")
    @Mapping(target = "actionTitle", expression = "java(entity.getEmail() + actionType.getTitle())")
    UserEventSend map(UserEntity entity, ActionType actionType, UUID resourceId);

    @Mapping(target = "userId", source = "entity.id")
    @Mapping(target = "prefectureId", source = "entity.prefecture.id")
    @Mapping(target = "resourceName", expression = "java(ResourceName.USERS)")
    @Mapping(target = "actionTitle", expression = "java(entity.getEmail() + actionType.getTitle())")
    @Mapping(target = "userToken", source ="token")
    UserEventSend map(UserEntity entity, String token, ActionType actionType, UUID resourceId);

    @Mapping(target = "userId", source = "entity.id")
    @Mapping(target = "prefectureId", source = "entity.prefecture.id")
    @Mapping(target = "userToken", source ="token")
    UserEventSend map(UserEntity entity, String token);

    @Mapping(target = "userId", source = "driverUpdated.id")
    @Mapping(target = "prefectureId", source = "driverUpdated.prefecture.id")
    @Mapping(target = "name", source = "driverUpdated.name")
    @Mapping(target = "email", source = "driverUpdated.email")
    @Mapping(target = "role", source = "driverUpdated.role")
    @Mapping(target = "cpf", source = "driverUpdated.cpf")
    @Mapping(target = "actorUserId", source = "admin.userId")
    @Mapping(target = "actorEmail", source = "admin.email")
    @Mapping(target = "actorRole", source = "admin.role")
    @Mapping(target = "resourceName", expression = "java(ResourceName.USERS)")
    @Mapping(target = "resourceId", source = "driverUpdated.id")
    @Mapping(target = "actionTitle", expression = "java(admin.email() + actionType.getTitle() + driverUpdated.getEmail())")
    DriverUpdatedByAdminAuditEventSend map(UserEntity driverUpdated, CurrentUser admin, ActionType actionType);
}
