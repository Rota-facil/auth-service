package com.rota.facil.auth_service.business.user;

import com.rota.facil.auth_service.business.TokenService;
import com.rota.facil.auth_service.http.dto.request.user.LoginRequestDTO;
import com.rota.facil.auth_service.http.dto.response.user.AccessTokenResponseDTO;
import com.rota.facil.auth_service.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AccessTokenResponseDTO execute(LoginRequestDTO request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication =  authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        String token = tokenService.generateAccessToken(userEntity);
        return new AccessTokenResponseDTO(token);
    }
}
