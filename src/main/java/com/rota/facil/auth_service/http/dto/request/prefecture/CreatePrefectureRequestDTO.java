package com.rota.facil.auth_service.http.dto.request.prefecture;

import com.rota.facil.auth_service.domain.enums.Region;

import java.util.UUID;

public record CreatePrefectureRequestDTO(
        UUID prefectureId,
        String name,
        Region region,
        PrefectureUser prefectureUser
) {
}
