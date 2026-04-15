package com.rota.facil.auth_service.messaging.mappers;

import com.rota.facil.auth_service.domain.enums.ActionType;
import com.rota.facil.auth_service.domain.enums.ResourceName;
import com.rota.facil.auth_service.messaging.dto.send.AuditUserEventSend;
import com.rota.facil.auth_service.messaging.dto.send.TransportUserCreatedEventSend;
import com.rota.facil.auth_service.messaging.dto.send.TransportUserDeletedEventSend;
import com.rota.facil.auth_service.persistence.entities.UserEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserEventMapper {
    default AuditUserEventSend mapAuditSend(UserEntity entity, ActionType actionType, UUID resourceId) {
        return new AuditUserEventSend(
                entity.getId(),
                entity.getRole(),
                entity.getEmail() + actionType.getTitle(),
                actionType,
                ResourceName.USERS,
                resourceId
        );
    }

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "prefectureId", source = "prefecture.id")
    TransportUserCreatedEventSend mapTransportUserCreatedSend(UserEntity entity);

    @Mapping(target = "userId", source = "id")
    TransportUserCreatedEventSend mapTransportUserUpdatedSend(UserEntity entity);

    @Mapping(target = "userId", source = "id")
    TransportUserDeletedEventSend mapTransportUserDeletedSend(UserEntity entity);
}
