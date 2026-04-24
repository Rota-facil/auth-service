package com.rota.facil.auth_service.http.controllers;

import com.rota.facil.auth_service.business.UserService;
import com.rota.facil.auth_service.http.dto.request.user.*;
import com.rota.facil.auth_service.http.dto.response.AccessTokenResponseDTO;
import com.rota.facil.auth_service.http.dto.response.UserResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

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
