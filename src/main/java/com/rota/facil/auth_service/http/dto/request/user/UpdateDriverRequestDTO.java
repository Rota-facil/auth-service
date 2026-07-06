package com.rota.facil.auth_service.http.dto.request.user;

public record UpdateDriverRequestDTO(
        String name,
        String cpf,
        String email
) {
}
