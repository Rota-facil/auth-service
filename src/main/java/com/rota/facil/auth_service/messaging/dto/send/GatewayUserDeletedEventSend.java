package com.rota.facil.auth_service.messaging.dto.send;


public record GatewayUserDeletedEventSend(
        String userToken
) {
}
