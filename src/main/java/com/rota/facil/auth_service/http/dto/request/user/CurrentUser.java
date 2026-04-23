package com.rota.facil.auth_service.http.dto.request.user;

import java.util.UUID;

public record CurrentUser(
        UUID userId,
        UUID prefectureId,
        String email,
        String role,
        String token
) {
}
