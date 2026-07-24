package com.rota.facil.auth_service.business.prefecture;

import com.rota.facil.auth_service.business.helpers.prefecture.FetchPrefectureHelper;
import com.rota.facil.auth_service.http.dto.request.prefecture.UpdatePrefectureRequestDTO;
import com.rota.facil.auth_service.http.dto.request.user.CurrentUser;
import com.rota.facil.auth_service.http.dto.response.prefecture.PrefectureResponseDTO;
import com.rota.facil.auth_service.messaging.producers.RabbitAuthPrefectureEventProducer;
import com.rota.facil.auth_service.persistence.entities.PrefectureEntity;
import com.rota.facil.auth_service.persistence.mappers.PrefectureMapper;
import com.rota.facil.auth_service.persistence.repositories.PrefectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdatePrefectureUseCase {
    private final PrefectureRepository prefectureRepository;
    private final RabbitAuthPrefectureEventProducer prefectureEventProducer;
    private final PrefectureMapper prefectureMapper;
    private final FetchPrefectureHelper fetchPrefectureHelper;

    public PrefectureResponseDTO execute(UUID prefectureId, UpdatePrefectureRequestDTO request, CurrentUser currentUser) {
        PrefectureEntity infoToUpdate = prefectureMapper.map(request);

        PrefectureEntity prefectureFound = fetchPrefectureHelper.execute(prefectureId);

        prefectureFound.update(infoToUpdate);

        PrefectureEntity updated = prefectureRepository.save(prefectureFound);

        prefectureEventProducer.updatePrefectureEvent(updated, currentUser);
        return prefectureMapper.map(updated);
    }
}
