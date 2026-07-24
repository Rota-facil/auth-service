package com.rota.facil.auth_service.business.user;

import com.rota.facil.auth_service.business.helpers.user.FetchUserHelper;
import com.rota.facil.auth_service.http.dto.request.user.CurrentUser;
import com.rota.facil.auth_service.http.dto.response.user.UserResponseDTO;
import com.rota.facil.auth_service.persistence.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FetchUserUseCase {
    private final FetchUserHelper fetchUserHelper;
    private final UserMapper userMapper;

    public UserResponseDTO execute(CurrentUser currentUser) {
        return userMapper.map(fetchUserHelper.execute(currentUser.userId()));
    }
}
