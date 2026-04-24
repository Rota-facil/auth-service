package com.rota.facil.auth_service.http.dto.request.prefecture;

import com.rota.facil.auth_service.domain.enums.Region;
import jakarta.validation.constraints.NotBlank;

public record CreatePrefectureRequestDTO(
        @NotBlank(message = "nome da prefeitura é obrigatório")
        String name,

        @NotBlank(message = "região é obrigatório")
        Region region,

        PrefectureUser prefectureUser
) {
}
