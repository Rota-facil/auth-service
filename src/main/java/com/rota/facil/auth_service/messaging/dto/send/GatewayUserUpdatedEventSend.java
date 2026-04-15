package com.rota.facil.auth_service.messaging.dto.send;

import java.util.UUID;

public record GatewayUserUpdatedEventSend(
        UUID userId,
        String userToken
) {
}
