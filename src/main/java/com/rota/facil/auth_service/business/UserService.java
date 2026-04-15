package com.rota.facil.auth_service.business;

import com.rota.facil.auth_service.domain.enums.ActionType;
import com.rota.facil.auth_service.domain.enums.Role;
import com.rota.facil.auth_service.domain.exceptions.PrefectureNotFoundException;
import com.rota.facil.auth_service.domain.exceptions.UserNotFoundException;
import com.rota.facil.auth_service.http.dto.request.CreateAccountRequestDTO;
import com.rota.facil.auth_service.http.dto.request.CurrentUser;
import com.rota.facil.auth_service.http.dto.request.LoginRequestDTO;
import com.rota.facil.auth_service.http.dto.request.UpdateAccountRequestDTO;
import com.rota.facil.auth_service.http.dto.response.AccessTokenResponseDTO;
import com.rota.facil.auth_service.http.dto.response.UserResponseDTO;
import com.rota.facil.auth_service.messaging.mappers.UserEventMapper;
import com.rota.facil.auth_service.messaging.producers.RabbitAuditEventProducer;
import com.rota.facil.auth_service.messaging.producers.RabbitFileEventProducer;
import com.rota.facil.auth_service.messaging.producers.RabbitGatewayEventProducer;
import com.rota.facil.auth_service.messaging.producers.RabbitTransportEventProducer;
import com.rota.facil.auth_service.persistence.entities.PrefectureEntity;
import com.rota.facil.auth_service.persistence.entities.UserEntity;
import com.rota.facil.auth_service.persistence.mappers.UserMapper;
import com.rota.facil.auth_service.persistence.repositories.PrefectureRepository;
import com.rota.facil.auth_service.persistence.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PrefectureRepository prefectureRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final RabbitAuditEventProducer rabbitAuditEventProducer;
    private final RabbitFileEventProducer rabbitFileEventProducer;
    private final RabbitGatewayEventProducer rabbitGatewayEventProducer;
    private final RabbitTransportEventProducer rabbitTransportEventProducer;
    private final UserMapper userMapper;
    private final UserEventMapper userEventMapper;

    public AccessTokenResponseDTO register(CreateAccountRequestDTO request) {
        PrefectureEntity prefectureFound = prefectureRepository.findById(request.prefectureId())
                .orElseThrow(PrefectureNotFoundException::new);

        UserEntity preSaved = userMapper.map(request);

        preSaved.setPrefecture(prefectureFound);
        preSaved.setPassword(passwordEncoder.encode(preSaved.getPassword()));
        preSaved.setRole(Role.STUDENT);

        UserEntity saved = userRepository.save(preSaved);

        String token = tokenService.generateAccessToken(saved);

        rabbitAuditEventProducer.createUserEvent(userEventMapper.mapAuditSend(saved, ActionType.CREATE, saved.getId()));
        rabbitTransportEventProducer.createUserEvent(userEventMapper.mapTransportUserCreatedSend(saved));
        return new AccessTokenResponseDTO(token);
    }

    public AccessTokenResponseDTO login(LoginRequestDTO request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication =  authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        String token = tokenService.generateAccessToken(userEntity);
        return new AccessTokenResponseDTO(token);
    }

    @Transactional
    public UserResponseDTO update(UpdateAccountRequestDTO request, CurrentUser currentUser) {
        UserEntity userFound = this.fetchEntity(currentUser.userId());

        boolean isDifferentEmail = userFound.isDifferentEmail(request.email());

        if (request.prefectureId() != null && !userFound.getPrefecture().getId().equals(request.prefectureId())) {
            PrefectureEntity prefectureFound = prefectureRepository.findById(request.prefectureId())
                    .orElseThrow(PrefectureNotFoundException::new);

            userFound.setPrefecture(null);
            userFound.setPrefecture(prefectureFound);
        }

        UserEntity infoToUpdate = userMapper.map(request);

        userFound.update(infoToUpdate);

        UserEntity updated = userRepository.save(userFound);

        if (isDifferentEmail) rabbitGatewayEventProducer.updateUserEvent(userEventMapper.mapGatewayUserUpdatedSend(updated, currentUser.token()));

        return userMapper.map(updated);
    }

    public UserResponseDTO fetch(CurrentUser currentUser) {
        return userMapper.map(this.fetchEntity(currentUser.userId()));
    }

    private UserEntity fetchEntity(UUID userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
}
