package com.rota.facil.auth_service.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PrefectureAuditAction {
    PREFECTURE_CREATED("CREATE", "%s criou a prefeitura %s"),
    PREFECTURE_UPDATED("UPDATE", "%s atualizou dados da prefeitura %s"),
    PREFECTURE_DELETED("DELETE", "%s deletou a prefeitura %s");

    private final String actionType;
    private final String titleTemplate;

    public String title(String actorEmail, String prefectureName) {
        return this.titleTemplate.formatted(actorEmail, prefectureName);
    }
}
