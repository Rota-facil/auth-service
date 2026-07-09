package com.rota.facil.auth_service.domain.exceptions;

public class AlreadyExistsUserEmail extends RuntimeException {
    public AlreadyExistsUserEmail(String message) {
        super(message);
    }
    public AlreadyExistsUserEmail() {
        super("Já existe usuario com este email");
    }
}
