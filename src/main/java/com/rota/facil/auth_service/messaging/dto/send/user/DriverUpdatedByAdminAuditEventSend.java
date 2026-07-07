package com.rota.facil.auth_service.messaging.dto.send.user;

import com.rota.facil.auth_service.domain.enums.ActionType;
import com.rota.facil.auth_service.domain.enums.ResourceName;
import com.rota.facil.auth_service.domain.enums.Role;

import java.util.UUID;

public record DriverUpdatedByAdminAuditEventSend(
        UUID userId,
        UUID prefectureId,
        String name,
        String email,
        Role role,
        String cpf,
        UUID actorUserId,
        String actorEmail,
        String actorRole,
        String actionTitle,
        ActionType actionType,
        ResourceName resourceName,
        UUID resourceId
) {
}
