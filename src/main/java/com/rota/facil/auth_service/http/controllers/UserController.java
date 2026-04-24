package com.rota.facil.auth_service.http.controllers;

import com.rota.facil.auth_service.business.UserService;
import com.rota.facil.auth_service.http.dto.request.user.*;
import com.rota.facil.auth_service.http.dto.response.AccessTokenResponseDTO;
import com.rota.facil.auth_service.http.dto.response.UserResponseDTO;
import com.rota.facil.auth_service.http.google.handler.AuthSuccessHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthSuccessHandler authSuccessHandler;

    @PostMapping("/driver/register")
    public ResponseEntity<AccessTokenResponseDTO> createDriverAccount(
            @Valid @RequestBody CreateUserAccountRequestDTO request,
            @AuthenticationPrincipal CurrentUser admin
    ) {
        return ResponseEntity.ok(userService.registerDriver(request, admin));
    }

    @PostMapping("/user/prefecture/register")
    public ResponseEntity<AccessTokenResponseDTO> createPrefectureAccount(
            @Valid @RequestBody CreateUserAccountRequestDTO request,
            @AuthenticationPrincipal CurrentUser admin
            ) {
        return ResponseEntity.ok(userService.registerUserPrefecture(request, admin));
    }

    @PostMapping("/google/complete-registration")
    public ResponseEntity<AccessTokenResponseDTO> completeGoogleRegistration(
            @RequestBody CompleteGoogleRegistrationRequestDTO request,
            @RequestParam UUID pendingToken
    ) {
        return ResponseEntity.ok(userService.completeGoogleRegistration(request, pendingToken));
    }

    @PostMapping("/register")
    public ResponseEntity<AccessTokenResponseDTO> createAccount(@Valid  @RequestBody CreateAccountRequestDTO request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/login")
    public ResponseEntity<AccessTokenResponseDTO> login (@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponseDTO> update(
            @AuthenticationPrincipal CurrentUser currentUser,
            @Valid @RequestBody UpdateAccountRequestDTO request
    ) {
        return ResponseEntity.ok(userService.update(request, currentUser));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> fetch(@AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(userService.fetch(currentUser));
    }
}
