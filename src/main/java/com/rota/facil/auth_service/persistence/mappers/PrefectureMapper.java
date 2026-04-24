package com.rota.facil.auth_service.persistence.mappers;

import com.rota.facil.auth_service.http.dto.request.prefecture.CreatePrefectureRequestDTO;
import com.rota.facil.auth_service.http.dto.response.prefecture.CreatePrefectureResponseDTO;
import com.rota.facil.auth_service.persistence.entities.PrefectureEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface PrefectureMapper {
    PrefectureEntity map(CreatePrefectureRequestDTO request);

    @Mapping(target = "accessToken", source = "accessToken")
    CreatePrefectureResponseDTO map(PrefectureEntity entity, String accessToken);
}
