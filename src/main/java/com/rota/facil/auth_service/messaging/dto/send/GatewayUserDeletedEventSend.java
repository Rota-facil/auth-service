package com.rota.facil.auth_service.messaging.dto.send;


import java.util.UUID;

public record GatewayUserDeletedEventSend(
        UUID userId,
        String userToken
) {
}
