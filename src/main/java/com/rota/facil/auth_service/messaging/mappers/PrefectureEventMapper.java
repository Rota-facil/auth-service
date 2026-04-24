package com.rota.facil.auth_service.messaging.mappers;

import com.rota.facil.auth_service.domain.enums.ActionType;
import com.rota.facil.auth_service.http.dto.request.user.CurrentUser;
import com.rota.facil.auth_service.messaging.dto.send.PrefectureEventSend;
import com.rota.facil.auth_service.persistence.entities.PrefectureEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface PrefectureEventMapper {
    @Mapping(target = "actionTitle", expression = "java(currentUser.email() + actionType.getPrefectureTitle() + entity.getName())")
    @Mapping(target = "resourceName", expression = "java(ResourceName.PREFECTURE)")
    @Mapping(target = "resourceId", source = "entity.id")
    PrefectureEventSend map(PrefectureEntity entity, CurrentUser currentUser, ActionType actionType);
}