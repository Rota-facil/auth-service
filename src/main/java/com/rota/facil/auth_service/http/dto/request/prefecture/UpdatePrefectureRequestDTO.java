package com.rota.facil.auth_service.http.dto.request.prefecture;

import com.rota.facil.auth_service.domain.enums.Region;

public record UpdatePrefectureRequestDTO(
        String name,
        Region region
) {
}
