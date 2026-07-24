package com.rota.facil.auth_service.business.user;

import com.rota.facil.auth_service.business.TokenService;
import com.rota.facil.auth_service.business.helpers.prefecture.FetchPrefectureHelper;
import com.rota.facil.auth_service.domain.enums.Role;
import com.rota.facil.auth_service.domain.enums.UserAuditAction;
import com.rota.facil.auth_service.domain.exceptions.AlreadyExistsUserCpf;
import com.rota.facil.auth_service.domain.exceptions.AlreadyExistsUserEmail;
import com.rota.facil.auth_service.domain.exceptions.PrefectureNotFoundException;
import com.rota.facil.auth_service.http.dto.request.user.CreateAccountRequestDTO;
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
public class CreateUserUseCase {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TokenService tokenService;
    private final FetchPrefectureHelper fetchPrefectureHelper;
    private final PasswordEncoder passwordEncoder;
    private final RabbitAuthUserEventProducer userEventProducer;

    public AccessTokenResponseDTO execute(CreateAccountRequestDTO request) {
        PrefectureEntity prefectureFound = fetchPrefectureHelper.execute(request.prefectureId());

        UserEntity preSaved  = userMapper.map(request);

        preSaved.setPrefecture(prefectureFound);
        preSaved.setPassword(passwordEncoder.encode(preSaved.getPassword()));
        preSaved.setRole(Role.STUDENT);


        if (userRepository.findByCpf(preSaved.getCpf()).isPresent()) throw new AlreadyExistsUserCpf();
        if (userRepository.findByEmailWithoutActiveProperty(preSaved.getEmail()).isPresent()) throw new AlreadyExistsUserEmail();

        UserEntity saved = userRepository.save(preSaved);

        String token = tokenService.generateAccessToken(saved);

        userEventProducer.createUserEvent(saved, UserAuditAction.STUDENT_CREATED_ACCOUNT);
        return new AccessTokenResponseDTO(token);
    }
}
