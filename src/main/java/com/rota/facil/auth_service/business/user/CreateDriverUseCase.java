package com.rota.facil.auth_service.business.user;

import com.rota.facil.auth_service.business.TokenService;
import com.rota.facil.auth_service.business.helpers.prefecture.FetchPrefectureHelper;
import com.rota.facil.auth_service.domain.enums.Role;
import com.rota.facil.auth_service.domain.exceptions.PrefectureNotFoundException;
import com.rota.facil.auth_service.http.dto.request.user.CreateUserAccountRequestDTO;
import com.rota.facil.auth_service.http.dto.request.user.CurrentUser;
import com.rota.facil.auth_service.http.dto.response.user.AccessTokenResponseDTO;
import com.rota.facil.auth_service.messaging.producers.RabbitAuthUserEventProducer;
import com.rota.facil.auth_service.persistence.entities.PrefectureEntity;
import com.rota.facil.auth_service.persistence.entities.UserEntity;
import com.rota.facil.auth_service.persistence.mappers.UserMapper;
import com.rota.facil.auth_service.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateDriverUseCase {
    private final FetchPrefectureHelper fetchPrefectureHelper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RabbitAuthUserEventProducer userEventProducer;
    private final TokenService tokenService;

    public AccessTokenResponseDTO execute(CreateUserAccountRequestDTO request, CurrentUser admin) {
        PrefectureEntity prefectureFound = fetchPrefectureHelper.execute(admin.prefectureId());

        UserEntity preSaved = userMapper.map(request);

        preSaved.setPrefecture(prefectureFound);
        preSaved.setPassword(passwordEncoder.encode(preSaved.getPassword()));
        preSaved.setRole(Role.DRIVER);

        UserEntity saved = userRepository.save(preSaved);

        String token = tokenService.generateAccessToken(saved);

        userEventProducer.createDriverByAdminEvent(saved, admin);
        return new AccessTokenResponseDTO(token);
    }
}
