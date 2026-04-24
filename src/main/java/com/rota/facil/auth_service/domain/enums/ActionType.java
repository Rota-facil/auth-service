package com.rota.facil.auth_service.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActionType {
    CREATE(" criou uma conta", " criou prefeitura "),
    UPDATE(" atualizou seus dados", " atualizou dados da prefeitura "),
    DELETE( "deletou sua conta", " deletou prefeitura ");

    private final String userTitle;
    private final String prefectureTitle;
}
