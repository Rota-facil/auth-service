package com.rota.facil.auth_service.business.prefecture;

import com.rota.facil.auth_service.business.helpers.prefecture.FetchPrefectureHelper;
import com.rota.facil.auth_service.http.dto.request.user.CurrentUser;
import com.rota.facil.auth_service.messaging.producers.RabbitAuthPrefectureEventProducer;
import com.rota.facil.auth_service.persistence.entities.PrefectureEntity;
import com.rota.facil.auth_service.persistence.repositories.PrefectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeletePrefectureUseCase {
    private final PrefectureRepository prefectureRepository;
    private final RabbitAuthPrefectureEventProducer prefectureEventProducer;
    private final FetchPrefectureHelper fetchPrefectureHelper;

    public void execute(UUID prefectureId, CurrentUser currentUser) {
        PrefectureEntity prefectureFound = fetchPrefectureHelper.execute(prefectureId);
        prefectureRepository.deleteById(prefectureId);

        prefectureEventProducer.deletePrefectureEvent(prefectureFound, currentUser);
    }
}
