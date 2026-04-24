package com.rota.facil.auth_service.business;

import com.rota.facil.auth_service.domain.exceptions.PrefectureNotFoundException;
import com.rota.facil.auth_service.http.dto.request.prefecture.CreatePrefectureRequestDTO;
import com.rota.facil.auth_service.http.dto.request.prefecture.UpdatePrefectureRequestDTO;
import com.rota.facil.auth_service.http.dto.request.user.CurrentUser;
import com.rota.facil.auth_service.http.dto.response.prefecture.CreatePrefectureResponseDTO;
import com.rota.facil.auth_service.http.dto.response.prefecture.PrefectureResponseDTO;
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

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrefectureService {
    private final TokenService tokenService;
    private final PrefectureRepository prefectureRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RabbitAuthPrefectureEventProducer prefectureEventProducer;
    private final PrefectureMapper prefectureMapper;
    private final UserMapper userMapper;

    public CreatePrefectureResponseDTO register(CreatePrefectureRequestDTO request, CurrentUser currentUser) {
        PrefectureEntity prefecturePreSaved = prefectureMapper.map(request);
        UserEntity defaultAdminPrefectureUser = userMapper.map(request.prefectureUser());

        PrefectureEntity prefectureSaved = prefectureRepository.save(prefecturePreSaved);

        defaultAdminPrefectureUser.setPrefecture(prefectureSaved);
        defaultAdminPrefectureUser.setPassword(passwordEncoder.encode(defaultAdminPrefectureUser.getPassword()));

        UserEntity savedDefaultAdminPrefectureUser = userRepository.save(defaultAdminPrefectureUser);

        String accessToken = tokenService.generateAccessToken(savedDefaultAdminPrefectureUser);

        prefectureEventProducer.createPrefectureEvent(prefectureSaved, currentUser);
        // precisa mandar evento para notification

        return prefectureMapper.map(prefectureSaved, accessToken);
    }

    public PrefectureResponseDTO fetch(UUID prefectureId) {
        return prefectureMapper.map(this.fetchEntity(prefectureId));
    }

    public List<PrefectureResponseDTO> list() {
        return prefectureRepository.findAll().stream()
                .map(prefectureMapper::map)
                .toList();
    }

    public PrefectureResponseDTO update(UUID prefectureId, UpdatePrefectureRequestDTO request, CurrentUser currentUser) {
        PrefectureEntity infoToUpdate = prefectureMapper.map(request);

        PrefectureEntity prefectureFound = this.fetchEntity(prefectureId);

        prefectureFound.update(infoToUpdate);

        PrefectureEntity updated = prefectureRepository.save(prefectureFound);

        prefectureEventProducer.updatePrefectureEvent(updated, currentUser);
        return prefectureMapper.map(updated);
    }

    public void delete(UUID prefectureId, CurrentUser currentUser) {
        PrefectureEntity prefectureFound = this.fetchEntity(prefectureId);
        prefectureRepository.deleteById(prefectureId);

        prefectureEventProducer.deletePrefectureEvent(prefectureFound, currentUser);
    }

    private PrefectureEntity fetchEntity(UUID prefectureId) {
        return prefectureRepository.findById(prefectureId)
                .orElseThrow(PrefectureNotFoundException::new);
    }
}
