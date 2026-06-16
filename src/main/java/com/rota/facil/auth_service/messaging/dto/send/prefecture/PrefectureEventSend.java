package com.rota.facil.auth_service.messaging.dto.send.prefecture;

import com.rota.facil.auth_service.domain.enums.ActionType;
import com.rota.facil.auth_service.domain.enums.ResourceName;
import com.rota.facil.auth_service.domain.enums.Role;

import java.util.UUID;

public record PrefectureEventSend(
        UUID userId,
        String email,
        Role role,
        String actionTitle,
        ActionType actionType,
        ResourceName resourceName,
        UUID resourceId
) {
}
