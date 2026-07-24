package com.rota.facil.auth_service.business.user;

import com.rota.facil.auth_service.domain.exceptions.UserNotFoundException;
import com.rota.facil.auth_service.http.dto.request.user.CurrentUser;
import com.rota.facil.auth_service.http.dto.request.user.UpdateDriverRequestDTO;
import com.rota.facil.auth_service.messaging.producers.RabbitAuthUserEventProducer;
import com.rota.facil.auth_service.persistence.entities.UserEntity;
import com.rota.facil.auth_service.persistence.mappers.UserMapper;
import com.rota.facil.auth_service.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateDriverUseCase {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RabbitAuthUserEventProducer userEventProducer;

    public void execute(UUID driverId, CurrentUser currentUser, UpdateDriverRequestDTO request) {
        UserEntity driverFound = userRepository.findDriverByIdAndPrefectureId(driverId, currentUser.prefectureId())
                .orElseThrow(UserNotFoundException::new);

        driverFound.update(userMapper.map(request));
        UserEntity updated = userRepository.save(driverFound);

        userEventProducer.updateDriverByAdminEvent(updated, currentUser);
    }
}
