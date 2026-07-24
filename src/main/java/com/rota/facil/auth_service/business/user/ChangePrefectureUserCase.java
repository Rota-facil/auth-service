package com.rota.facil.auth_service.business.user;

import com.rota.facil.auth_service.business.helpers.prefecture.FetchPrefectureHelper;
import com.rota.facil.auth_service.business.helpers.user.FetchUserHelper;
import com.rota.facil.auth_service.domain.enums.UserAuditAction;
import com.rota.facil.auth_service.http.dto.request.user.CurrentUser;
import com.rota.facil.auth_service.messaging.producers.RabbitAuthUserEventProducer;
import com.rota.facil.auth_service.persistence.entities.PrefectureEntity;
import com.rota.facil.auth_service.persistence.entities.UserEntity;
import com.rota.facil.auth_service.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChangePrefectureUserCase {
    private final FetchUserHelper fetchUserHelper;
    private final FetchPrefectureHelper fetchPrefectureHelper;
    private final UserRepository userRepository;
    private final RabbitAuthUserEventProducer userEventProducer;

    public void execute(CurrentUser currentUser, UUID prefectureId) {
        UserEntity userFound = fetchUserHelper.execute(currentUser.userId());
        PrefectureEntity prefectureFound = fetchPrefectureHelper.execute(prefectureId);

        userFound.setPrefecture(prefectureFound);

        UserEntity saved = userRepository.save(userFound);
        userEventProducer.updateUserEvent(saved, UserAuditAction.USER_UPDATED_OWN_ACCOUNT);
    }
}
