package com.rota.facil.auth_service.http.dto.request.prefecture;

import com.rota.facil.auth_service.domain.enums.Region;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePrefectureRequestDTO(
        @NotBlank(message = "nome da prefeitura é obrigatório")
        String name,

        @NotNull(message = "região é obrigatório")
        Region region,

        @NotNull(message = "usuario default da prefeitura eh obrigatório")
        PrefectureUser prefectureUser
) {
}
