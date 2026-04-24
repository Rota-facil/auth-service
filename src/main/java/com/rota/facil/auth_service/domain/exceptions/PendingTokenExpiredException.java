package com.rota.facil.auth_service.domain.exceptions;

public class PendingTokenExpiredException extends RuntimeException {
    public PendingTokenExpiredException(String message) {
        super(message);
    }

  public PendingTokenExpiredException() {
    super("Tempo para completar login com google expirou. Tente novamente");
  }
}
