package com.rota.facil.auth_service.business.user;

import com.rota.facil.auth_service.domain.exceptions.UserNotFoundException;
import com.rota.facil.auth_service.http.dto.request.user.CurrentUser;
import com.rota.facil.auth_service.messaging.producers.RabbitAuthUserEventProducer;
import com.rota.facil.auth_service.persistence.entities.UserEntity;
import com.rota.facil.auth_service.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeactivateDriverUseCase {
    private final UserRepository userRepository;
    private final RabbitAuthUserEventProducer userEventProducer;

    public void execute(CurrentUser currentUser, UUID driverId) {
        UserEntity driverFound = userRepository.findDriverByIdAndPrefectureId(driverId, currentUser.prefectureId()).orElseThrow(UserNotFoundException::new);
        driverFound.setActive(false);
        userRepository.save(driverFound);

        userEventProducer.adminDeactivateUserEvent(driverFound, currentUser);
    }
}
