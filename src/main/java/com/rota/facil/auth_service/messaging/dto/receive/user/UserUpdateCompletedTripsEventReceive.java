package com.rota.facil.auth_service.messaging.dto.receive.user;

import java.util.List;
import java.util.UUID;

public record UserUpdateCompletedTripsEventReceive(
        List<UUID> userIds
) {
}
