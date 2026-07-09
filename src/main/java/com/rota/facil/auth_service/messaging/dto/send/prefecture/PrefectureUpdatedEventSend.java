package com.rota.facil.auth_service.messaging.dto.send.prefecture;

import java.util.UUID;

public record PrefectureUpdatedEventSend(
        UUID userId,
        String email,
        String role,
        String actionTitle,
        String actionType,
        String resourceName,
        UUID resourceId,
        UUID prefectureId,
        String prefectureName
) {
}
