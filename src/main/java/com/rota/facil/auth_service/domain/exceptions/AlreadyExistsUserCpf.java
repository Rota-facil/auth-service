package com.rota.facil.auth_service.domain.exceptions;

public class AlreadyExistsUserCpf extends RuntimeException {
    public AlreadyExistsUserCpf(String message) {
        super(message);
    }
    public AlreadyExistsUserCpf() {
        super("Já existe usuário com este cpf");
    }
}
