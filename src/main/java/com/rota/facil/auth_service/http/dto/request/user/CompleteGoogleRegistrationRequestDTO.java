package com.rota.facil.auth_service.http.dto.request.user;

import java.util.UUID;

public record CompleteGoogleRegistrationRequestDTO(
        String cpf,
        UUID prefectureId
) {
}
