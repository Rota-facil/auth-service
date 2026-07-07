package com.rota.facil.auth_service.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserActionType implements ActionType{
    CREATE(" criou uma conta"),
    UPDATE(" atualizou seus dados"),
    ADMIN_UPDATE_DRIVER(" atualizou dados do motorista "),
    DELETE( "deletou sua conta"),
    DEACTIVATE( "desativou sua conta");

    private final String title;

    @Override
    public String getTitle() {
        return this.title;
    }
}
