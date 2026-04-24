package com.rota.facil.auth_service.http.dto.response.prefecture;

import com.rota.facil.auth_service.domain.enums.Region;

import java.util.UUID;

public record CreatePrefectureResponseDTO (
        UUID id,
        String name,
        Region region,
        String accessToken
) {
}
