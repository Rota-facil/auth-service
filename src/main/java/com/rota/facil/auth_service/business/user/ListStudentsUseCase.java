package com.rota.facil.auth_service.business.user;

import com.rota.facil.auth_service.http.dto.request.user.CurrentUser;
import com.rota.facil.auth_service.http.dto.response.user.UserResponseDTO;
import com.rota.facil.auth_service.persistence.mappers.UserMapper;
import com.rota.facil.auth_service.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListStudentsUseCase {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Page<UserResponseDTO> execute(CurrentUser currentUser, Pageable pageable) {
        return userRepository.findAllStudentsByPrefectureId(currentUser.prefectureId(), pageable)
                .map(userMapper::map);
    }
}
