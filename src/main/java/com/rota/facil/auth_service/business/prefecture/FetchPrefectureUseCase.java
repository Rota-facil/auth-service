package com.rota.facil.auth_service.business.prefecture;

import com.rota.facil.auth_service.business.helpers.prefecture.FetchPrefectureHelper;
import com.rota.facil.auth_service.http.dto.response.prefecture.PrefectureResponseDTO;
import com.rota.facil.auth_service.persistence.mappers.PrefectureMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FetchPrefectureUseCase {
    private final FetchPrefectureHelper fetchPrefectureHelper;
    private final PrefectureMapper prefectureMapper;

    public PrefectureResponseDTO execute(UUID prefectureId) {
        return prefectureMapper.map(fetchPrefectureHelper.execute(prefectureId));
    }
}
