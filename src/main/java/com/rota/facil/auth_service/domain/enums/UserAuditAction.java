package com.rota.facil.auth_service.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserAuditAction {
    STUDENT_CREATED_ACCOUNT("CREATE", "%s criou a propria conta"),
    DRIVER_CREATED_BY_ADMIN("CREATE", "%s criou o motorista %s"),
    USER_UPDATED_OWN_ACCOUNT("UPDATE", "%s atualizou os proprios dados"),
    USER_EMAIL_CHANGED("UPDATE", "%s alterou o proprio email"),
    DRIVER_UPDATED_BY_ADMIN("UPDATE", "%s atualizou dados do motorista %s"),
    USER_DELETED_OWN_ACCOUNT("DELETE", "%s deletou a propria conta"),
    USER_DEACTIVATED_OWN_ACCOUNT("DEACTIVATE", "%s desativou a propria conta"),
    DRIVER_DEACTIVATED_BY_ADMIN("DEACTIVATE", "%s desativou a conta do motorista %s"),
    USER_LOGOUT("LOGOUT", "%s fez logout");

    private final String actionType;
    private final String titleTemplate;

    public String title(String actorEmail) {
        return this.titleTemplate.formatted(actorEmail);
    }

    public String title(String actorEmail, String targetEmail) {
        return this.titleTemplate.formatted(actorEmail, targetEmail);
    }
}
