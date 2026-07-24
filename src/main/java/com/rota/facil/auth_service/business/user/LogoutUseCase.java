package com.rota.facil.auth_service.business.user;

import com.rota.facil.auth_service.business.helpers.user.FetchUserHelper;
import com.rota.facil.auth_service.http.dto.request.user.CurrentUser;
import com.rota.facil.auth_service.messaging.producers.RabbitAuthUserEventProducer;
import com.rota.facil.auth_service.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutUseCase {
    private final FetchUserHelper fetchUserHelper;
    private final RabbitAuthUserEventProducer userEventProducer;

    public void execute(CurrentUser currentUser) {
        UserEntity userFound = fetchUserHelper.execute(currentUser.userId());
        userEventProducer.logout(userFound, currentUser.token());
    }
}
