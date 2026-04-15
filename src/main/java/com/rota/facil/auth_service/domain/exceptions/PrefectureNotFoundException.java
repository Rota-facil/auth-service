package com.rota.facil.auth_service.domain.exceptions;

public class PrefectureNotFoundException extends RuntimeException {
    public PrefectureNotFoundException(String message) {
        super(message);
    }

    public PrefectureNotFoundException() {
        super("Prefeitura não foi encontrada");
    }
}
