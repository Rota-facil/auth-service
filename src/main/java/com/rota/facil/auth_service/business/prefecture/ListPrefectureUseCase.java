package com.rota.facil.auth_service.business.prefecture;

import com.rota.facil.auth_service.http.dto.response.prefecture.PrefectureResponseDTO;
import com.rota.facil.auth_service.persistence.mappers.PrefectureMapper;
import com.rota.facil.auth_service.persistence.repositories.PrefectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListPrefectureUseCase {
    private final PrefectureRepository prefectureRepository;
    private final PrefectureMapper prefectureMapper;

    public List<PrefectureResponseDTO> execute() {
        return prefectureRepository.findAll().stream()
                .map(prefectureMapper::map)
                .toList();
    }
}
