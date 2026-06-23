package com.rota.facil.auth_service.http.dto.response.user;

import com.rota.facil.auth_service.domain.enums.Region;

import java.util.UUID;

public record UserPrefectureResponseDTO(
        UUID id,
        String name,
        Region region
) {
}
