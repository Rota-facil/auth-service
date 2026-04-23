package com.rota.facil.auth_service.http.dto.request.user;

import org.hibernate.validator.constraints.br.CPF;

import java.util.UUID;

public record UpdateAccountRequestDTO(
        UUID prefectureId,
        String name,
        String email,

        @CPF(message = "o CPF tem que ser válido")
        String cpf
) {
}
