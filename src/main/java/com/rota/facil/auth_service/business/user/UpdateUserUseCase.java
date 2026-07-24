package com.rota.facil.auth_service.business.user;

import com.rota.facil.auth_service.business.helpers.prefecture.FetchPrefectureHelper;
import com.rota.facil.auth_service.business.helpers.user.FetchUserHelper;
import com.rota.facil.auth_service.domain.enums.UserAuditAction;
import com.rota.facil.auth_service.domain.exceptions.PrefectureNotFoundException;
import com.rota.facil.auth_service.http.dto.request.user.CurrentUser;
import com.rota.facil.auth_service.http.dto.request.user.UpdateAccountRequestDTO;
import com.rota.facil.auth_service.http.dto.response.user.UserResponseDTO;
import com.rota.facil.auth_service.messaging.producers.RabbitAuthUserEventProducer;
import com.rota.facil.auth_service.persistence.entities.PrefectureEntity;
import com.rota.facil.auth_service.persistence.entities.UserEntity;
import com.rota.facil.auth_service.persistence.mappers.UserMapper;
import com.rota.facil.auth_service.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCase {
    private final FetchUserHelper fetchUserHelper;
    private final FetchPrefectureHelper fetchPrefectureHelper;
    private final UserRepository userRepository;
    private final RabbitAuthUserEventProducer userEventProducer;
    private final UserMapper userMapper;

    @Transactional
    public UserResponseDTO execute(UpdateAccountRequestDTO request, CurrentUser currentUser) {
        UserEntity userFound = fetchUserHelper.execute(currentUser.userId());

        boolean isDifferentEmail = userFound.isDifferentEmail(request.email());

        if (request.prefectureId() != null && !userFound.getPrefecture().getId().equals(request.prefectureId())) {
            PrefectureEntity prefectureFound = fetchPrefectureHelper.execute(request.prefectureId());

            userFound.setPrefecture(null);
            userFound.setPrefecture(prefectureFound);
        }

        UserEntity infoToUpdate = userMapper.map(request);

        userFound.update(infoToUpdate);

        UserEntity updated = userRepository.save(userFound);

        if (isDifferentEmail) userEventProducer.emailChangedUserEvent(updated, currentUser.token());

        userEventProducer.updateUserEvent(updated, UserAuditAction.USER_UPDATED_OWN_ACCOUNT);

        return userMapper.map(updated);
    }
}
