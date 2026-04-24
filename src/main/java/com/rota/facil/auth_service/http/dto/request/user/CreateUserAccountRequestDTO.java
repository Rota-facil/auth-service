package com.rota.facil.auth_service.http.dto.request.user;

import com.rota.facil.auth_service.domain.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record CreateUserAccountRequestDTO(
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
