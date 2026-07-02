package com.rota.facil.auth_service.http.dto.response.user;

import com.rota.facil.auth_service.domain.enums.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        String cpf,
        UserPrefectureResponseDTO prefecture,
        Boolean active,
        Long completedTrips,
        Double score,
        Role role,
        LocalDateTime createdAt
) {
}
