package com.rota.facil.auth_service.business.user;

import com.rota.facil.auth_service.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IncreaseUserTripsUseCase {
    private final UserRepository userRepository;

    @Transactional
    public void execute(List<UUID> userIds) {
        userRepository.increaseTripsByUserIds(userIds);
    }
}
