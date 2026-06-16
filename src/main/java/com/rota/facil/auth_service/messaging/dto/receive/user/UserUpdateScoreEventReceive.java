package com.rota.facil.auth_service.messaging.dto.receive.user;

import java.util.UUID;

public record UserUpdateScoreEventReceive(
        UUID userId,
        double note
) {
}
