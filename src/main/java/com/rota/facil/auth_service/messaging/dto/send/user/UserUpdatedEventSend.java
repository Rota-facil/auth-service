package com.rota.facil.auth_service.messaging.dto.send.user;

import com.rota.facil.auth_service.domain.enums.Role;

import java.util.UUID;

public record UserUpdatedEventSend(
        UUID userId,
        UUID prefectureId,
        String name,
        String email,
        Role role,
        String cpf,
        String userToken,
        Boolean active,
        UUID actorUserId,
        String actorEmail,
        String actorRole,
        String actionTitle,
        String actionType,
        String resourceName,
        UUID resourceId
) {
}
