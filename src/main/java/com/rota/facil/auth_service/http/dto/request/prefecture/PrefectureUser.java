package com.rota.facil.auth_service.http.dto.request.prefecture;

import jakarta.validation.constraints.NotBlank;

public record PrefectureUser(
        @NotBlank(message = "nome é obrigatório")
        String name,

        @NotBlank(message = "email é obrigatório")
        String email,

        @NotBlank(message = "cpf é obrigatório")
        String cpf,

        @NotBlank(message = "senha é obrigatório")
        String password
) {
}
