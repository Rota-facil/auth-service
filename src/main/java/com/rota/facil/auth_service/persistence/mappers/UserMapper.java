package com.rota.facil.auth_service.persistence.mappers;

import com.rota.facil.auth_service.http.dto.request.prefecture.PrefectureUser;
import com.rota.facil.auth_service.http.dto.request.user.CreateAccountRequestDTO;
import com.rota.facil.auth_service.http.dto.request.user.CreateUserAccountRequestDTO;
import com.rota.facil.auth_service.http.dto.request.user.UpdateAccountRequestDTO;
import com.rota.facil.auth_service.http.dto.response.UserResponseDTO;
import com.rota.facil.auth_service.persistence.entities.UserEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserMapper {
    UserEntity map(CreateAccountRequestDTO request);
    UserEntity map(CreateUserAccountRequestDTO request);
    UserEntity map(UpdateAccountRequestDTO request);
    UserResponseDTO map(UserEntity entity);
    UserEntity map(PrefectureUser prefectureUser);
}
