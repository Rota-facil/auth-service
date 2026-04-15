package com.rota.facil.auth_service.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActionType {
    CREATE(" criou uma conta"),
    UPDATE(" atualizou seus dados"),
    DELETE( "deletou sua conta");

    private final String title;
}
