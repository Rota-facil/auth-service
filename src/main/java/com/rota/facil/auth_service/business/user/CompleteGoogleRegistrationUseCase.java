package com.rota.facil.auth_service.business.user;

import com.rota.facil.auth_service.business.TokenService;
import com.rota.facil.auth_service.business.helpers.prefecture.FetchPrefectureHelper;
import com.rota.facil.auth_service.domain.enums.UserAuditAction;
import com.rota.facil.auth_service.domain.exceptions.CompleteGoogleLoginException;
import com.rota.facil.auth_service.domain.exceptions.PendingTokenExpiredException;
import com.rota.facil.auth_service.http.dto.request.user.CompleteGoogleRegistrationRequestDTO;
import com.rota.facil.auth_service.http.dto.response.user.AccessTokenResponseDTO;
import com.rota.facil.auth_service.messaging.producers.RabbitAuthUserEventProducer;
import com.rota.facil.auth_service.persistence.entities.PrefectureEntity;
import com.rota.facil.auth_service.persistence.entities.TokenCompleteGoogleLoginEntity;
import com.rota.facil.auth_service.persistence.entities.UserEntity;
import com.rota.facil.auth_service.persistence.repositories.TokenCompleteGoogleLoginRepository;
import com.rota.facil.auth_service.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompleteGoogleRegistrationUseCase {
    private final FetchPrefectureHelper fetchPrefectureHelper;
    private final TokenCompleteGoogleLoginRepository tokenCompleteGoogleLoginRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final RabbitAuthUserEventProducer userEventProducer;

    public AccessTokenResponseDTO execute(CompleteGoogleRegistrationRequestDTO request, UUID pendingToken) {
        TokenCompleteGoogleLoginEntity googleLoginEntity = tokenCompleteGoogleLoginRepository.findByToken(pendingToken)
                .orElseThrow(CompleteGoogleLoginException::new);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tokenCreation = googleLoginEntity.getExpiration();

        if (now.isAfter(tokenCreation.plusMinutes(15))) {
            tokenCompleteGoogleLoginRepository.deleteByToken(pendingToken);
            throw new PendingTokenExpiredException();
        }

        UserEntity user = googleLoginEntity.getUser();

        PrefectureEntity prefectureFound = fetchPrefectureHelper.execute(request.prefectureId());


        user.setCpf(request.cpf());
        user.setPrefecture(prefectureFound);

        UserEntity saved = userRepository.save(user);

        tokenCompleteGoogleLoginRepository.deleteByToken(pendingToken);

        userEventProducer.createUserEvent(user, UserAuditAction.STUDENT_CREATED_ACCOUNT);
        return new AccessTokenResponseDTO(tokenService.generateAccessToken(saved));
    }
}
