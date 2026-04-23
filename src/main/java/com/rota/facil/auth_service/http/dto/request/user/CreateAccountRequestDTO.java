package com.rota.facil.auth_service.http.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;


import java.util.UUID;

public record CreateAccountRequestDTO(
        @NotNull(message = "id de prefeitura é obrigatório")
        UUID prefectureId,

        @NotBlank(message = "nome é obrigatório")
        String name,

        @NotBlank(message = "email é obrigatorio")
        String email,

        @CPF
        @NotBlank(message = "cpf é obrigatório")
        String cpf,

        @NotBlank(message = "senha é obrigatória")
        String password
) {
}
