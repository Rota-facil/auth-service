package com.rota.facil.auth_service.messaging.dto.send;

import com.rota.facil.auth_service.domain.enums.ActionType;
import com.rota.facil.auth_service.domain.enums.Role;
import com.rota.facil.auth_service.domain.enums.ResourceName;
import java.util.UUID;

public record AuditUserEventSend (
        UUID userId,
        Role role,
        String actionTitle,
        ActionType actionType,
        ResourceName resourceName,
        UUID resourceId
) {
}
