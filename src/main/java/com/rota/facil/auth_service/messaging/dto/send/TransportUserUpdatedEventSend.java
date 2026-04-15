package com.rota.facil.auth_service.messaging.dto.send;

import java.util.UUID;

public record TransportUserUpdatedEventSend(
        UUID userId,
        String name,
        String email
) {
}
