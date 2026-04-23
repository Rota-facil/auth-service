package com.rota.facil.auth_service.business;

import com.rota.facil.auth_service.domain.enums.Role;
import com.rota.facil.auth_service.domain.exceptions.PrefectureNotFoundException;
import com.rota.facil.auth_service.domain.exceptions.UserNotFoundException;
import com.rota.facil.auth_service.http.dto.request.user.*;
import com.rota.facil.auth_service.http.dto.response.AccessTokenResponseDTO;
import com.rota.facil.auth_service.http.dto.response.UserResponseDTO;
import com.rota.facil.auth_service.messaging.producers.*;
import com.rota.facil.auth_service.persistence.entities.PrefectureEntity;
import com.rota.facil.auth_service.persistence.entities.UserEntity;
import com.rota.facil.auth_service.persistence.mappers.UserMapper;
import com.rota.facil.auth_service.persistence.repositories.PrefectureRepository;
import com.rota.facil.auth_service.persistence.repositories.UserRepository;
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
    private final RabbitAuthUserEventProducer userEventProducer;
    private final UserMapper userMapper;

    public AccessTokenResponseDTO register(CreateAccountRequestDTO request) {
        PrefectureEntity prefectureFound = prefectureRepository.findById(request.prefectureId())
                .orElseThrow(PrefectureNotFoundException::new);

        UserEntity preSaved  = userMapper.map(request);

        preSaved.setPrefecture(prefectureFound);
        preSaved.setPassword(passwordEncoder.encode(preSaved.getPassword()));
        preSaved.setRole(Role.STUDENT);

        UserEntity saved = userRepository.save(preSaved);

        String token = tokenService.generateAccessToken(saved);

        userEventProducer.createUserEvent(saved);
        return new AccessTokenResponseDTO(token);
    }

    public AccessTokenResponseDTO registerDriver(CreateDriverAccountRequestDTO request, CurrentUser admin) {
        PrefectureEntity prefectureFound = prefectureRepository.findById(admin.prefectureId())
                .orElseThrow(PrefectureNotFoundException::new);

        UserEntity preSaved = userMapper.map(request);

        preSaved.setPrefecture(prefectureFound);
        preSaved.setPassword(passwordEncoder.encode(preSaved.getPassword()));
        preSaved.setRole(Role.DRIVER);

        UserEntity saved = userRepository.save(preSaved);

        String token = tokenService.generateAccessToken(saved);

        userEventProducer.createUserEvent(saved);
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

        if (isDifferentEmail) userEventProducer.emailChangedUserEvent(updated, currentUser.token());

        userEventProducer.updateUserEvent(updated);

        return userMapper.map(updated);
    }

    public UserResponseDTO fetch(CurrentUser currentUser) {
        return userMapper.map(this.fetchEntity(currentUser.userId()));
    }

    private UserEntity fetchEntity(UUID userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
}
