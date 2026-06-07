package com.rota.facil.auth_service.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PrefectureActionType implements ActionType {
    CREATE(" criou prefeitura "),
    UPDATE(" atualizou dados da prefeitura "),
    DELETE(" deletou prefeitura ");

    private final String title;

    @Override
    public String getTitle() {
        return this.title;
    }
}
