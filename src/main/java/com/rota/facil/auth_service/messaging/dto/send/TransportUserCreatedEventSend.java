package com.rota.facil.auth_service.messaging.dto.send;

import com.rota.facil.auth_service.domain.enums.Role;

import java.util.UUID;

public record TransportUserCreatedEventSend(
        UUID userId,
        UUID prefectureId,
        String name,
        String email,
        Role role
) {
}
