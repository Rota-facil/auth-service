package com.rota.facil.auth_service.http.dto.response;

import com.rota.facil.auth_service.domain.enums.Role;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        String cpf,
        Role role
) {
}
