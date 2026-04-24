package com.rota.facil.auth_service.domain.exceptions;

public class CompleteGoogleLoginException extends RuntimeException {
    public CompleteGoogleLoginException(String message) {
        super(message);
    }

    public CompleteGoogleLoginException() {
        super("Erro ao completar login com google");
    }
}
