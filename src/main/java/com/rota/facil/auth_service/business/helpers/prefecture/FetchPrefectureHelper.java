package com.rota.facil.auth_service.business.helpers.prefecture;

import com.rota.facil.auth_service.domain.exceptions.PrefectureNotFoundException;
import com.rota.facil.auth_service.persistence.entities.PrefectureEntity;
import com.rota.facil.auth_service.persistence.repositories.PrefectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FetchPrefectureHelper {
    private final PrefectureRepository prefectureRepository;

    public PrefectureEntity execute(UUID prefectureId) {
        return prefectureRepository.findById(prefectureId).orElseThrow(PrefectureNotFoundException::new);
    }
}
