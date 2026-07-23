package com.rota.facil.auth_service.business.prefecture;

import com.rota.facil.auth_service.business.TokenService;
import com.rota.facil.auth_service.domain.enums.Role;
import com.rota.facil.auth_service.http.dto.request.prefecture.CreatePrefectureRequestDTO;
import com.rota.facil.auth_service.http.dto.request.user.CurrentUser;
import com.rota.facil.auth_service.http.dto.response.prefecture.CreatePrefectureResponseDTO;
import com.rota.facil.auth_service.messaging.producers.RabbitAuthPrefectureEventProducer;
import com.rota.facil.auth_service.persistence.entities.PrefectureEntity;
import com.rota.facil.auth_service.persistence.entities.UserEntity;
import com.rota.facil.auth_service.persistence.mappers.PrefectureMapper;
import com.rota.facil.auth_service.persistence.mappers.UserMapper;
import com.rota.facil.auth_service.persistence.repositories.PrefectureRepository;
import com.rota.facil.auth_service.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreatePrefectureUseCase {
    private final PrefectureRepository prefectureRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final RabbitAuthPrefectureEventProducer prefectureEventProducer;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final PrefectureMapper prefectureMapper;

    @Transactional
    public CreatePrefectureResponseDTO execute(CreatePrefectureRequestDTO request, CurrentUser currentUser) {
        PrefectureEntity prefecturePreSaved = prefectureMapper.map(request);
        UserEntity defaultAdminPrefectureUser = userMapper.map(request.prefectureUser());

        PrefectureEntity prefectureSaved = prefectureRepository.save(prefecturePreSaved);

        defaultAdminPrefectureUser.setPrefecture(prefectureSaved);
        defaultAdminPrefectureUser.setPassword(passwordEncoder.encode(defaultAdminPrefectureUser.getPassword()));
        defaultAdminPrefectureUser.setRole(Role.ADMIN);

        UserEntity savedDefaultAdminPrefectureUser = userRepository.save(defaultAdminPrefectureUser);

        String accessToken = tokenService.generateAccessToken(savedDefaultAdminPrefectureUser);

        prefectureEventProducer.createPrefectureEvent(prefectureSaved, currentUser);
        // precisa mandar evento para notification

        return prefectureMapper.map(prefectureSaved, accessToken);
    }
}
