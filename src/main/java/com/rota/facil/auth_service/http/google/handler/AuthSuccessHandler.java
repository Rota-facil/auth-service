package com.rota.facil.auth_service.http.google.handler;

import com.rota.facil.auth_service.business.TokenService;
import com.rota.facil.auth_service.domain.enums.Role;
import com.rota.facil.auth_service.persistence.entities.TokenCompleteGoogleLoginEntity;
import com.rota.facil.auth_service.persistence.entities.UserEntity;
import com.rota.facil.auth_service.persistence.repositories.TokenCompleteGoogleLoginRepository;
import com.rota.facil.auth_service.persistence.repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final TokenCompleteGoogleLoginRepository tokenCompleteGoogleLoginRepository;
    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User googleUser = oAuth2AuthenticationToken.getPrincipal();

        String email = googleUser.getAttribute("email");
        String name =  googleUser.getAttribute("name");
        String googleId = googleUser.getAttribute("sub");

        UserEntity user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    return userRepository.save(
                            UserEntity.builder()
                                    .email(email)
                                    .name(name)
                                    .googleId(googleId)
                                    .role(Role.STUDENT)
                                    .build()
                    );
                });


        if (user.getPrefecture() != null && user.getCpf() != null) {
            String accessToken = tokenService.generateAccessToken(user);
            response.setContentType("application/json");
            response.getWriter().write("{\"accessToken\": \"" + accessToken + "\"}");
        } else {
            UUID pendingToken = UUID.randomUUID();

            tokenCompleteGoogleLoginRepository.findByUserId(user.getId())
                    .map(token -> {
                        token.setPendingToken(pendingToken);
                        return tokenCompleteGoogleLoginRepository.save(token);
                    })
                            .orElseGet(() -> tokenCompleteGoogleLoginRepository.save(
                                    TokenCompleteGoogleLoginEntity.builder()
                                            .user(user)
                                            .pendingToken(pendingToken)
                                            .build()
                            ));

            response.setContentType("application/json");
            response.getWriter().write("{\"completeLoginToken\": \"" + pendingToken.toString() + "\"}");
        }
    }
}
