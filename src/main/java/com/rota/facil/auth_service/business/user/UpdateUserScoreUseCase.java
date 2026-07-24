package com.rota.facil.auth_service.business.user;

import com.rota.facil.auth_service.business.helpers.user.FetchUserHelper;
import com.rota.facil.auth_service.persistence.entities.UserEntity;
import com.rota.facil.auth_service.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserScoreUseCase {
    private final FetchUserHelper fetchUserHelper;
    private final UserRepository userRepository;

    public void execute(UUID userId, double note) {
        UserEntity userFound = fetchUserHelper.execute(userId);
        userFound.setScore(note);
        userRepository.save(userFound);
    }
}
