package com.rota.facil.auth_service.persistence.mappers;

import com.rota.facil.auth_service.http.dto.request.CreateAccountRequestDTO;
import com.rota.facil.auth_service.persistence.entities.UserEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserMapper {
    UserEntity map(CreateAccountRequestDTO request);
}
