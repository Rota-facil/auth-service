package com.rota.facil.auth_service.http.controllers;

import com.rota.facil.auth_service.business.UserService;
import com.rota.facil.auth_service.http.dto.request.user.*;
import com.rota.facil.auth_service.http.dto.response.user.AccessTokenResponseDTO;
import com.rota.facil.auth_service.http.dto.response.user.UserResponseDTO;
import com.rota.facil.auth_service.http.google.handler.AuthSuccessHandler;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthSuccessHandler authSuccessHandler;

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal CurrentUser currentUser) {
        userService.logout(currentUser);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/user/prefecture/{prefectureId}/change")
    public ResponseEntity<Void> changePrefecture(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable UUID prefectureId) {
        userService.changePrefecture(currentUser, prefectureId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/driver/register")
    public ResponseEntity<AccessTokenResponseDTO> createDriverAccount(
            @Valid @RequestBody CreateUserAccountRequestDTO request,
            @AuthenticationPrincipal CurrentUser admin
    ) {
        return ResponseEntity.ok(userService.registerDriver(request, admin));
    }

    @PutMapping("/driver/{driverId}/update")
    public ResponseEntity<Void> updateDriver(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID driverId,
            @RequestBody UpdateDriverRequestDTO request
    ) {
        userService.updateDriver(driverId, currentUser, request);
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
            @RequestParam String pendingToken
    ) {
        return ResponseEntity.ok(userService.completeGoogleRegistration(request, UUID.fromString(pendingToken)));
    }

    @PostMapping("/register")
    public ResponseEntity<AccessTokenResponseDTO> createAccount(@Valid  @RequestBody CreateAccountRequestDTO request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/user/login")
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
        System.out.println("CHEGOU AQUI CHEGOU AQUI");
        return ResponseEntity.ok(userService.fetch(currentUser));
    }

    @PatchMapping("/deactivate")
    public ResponseEntity<Void> deactivateAccount(@AuthenticationPrincipal CurrentUser currentUser) {
        userService.deactivate(currentUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal CurrentUser currentUser) {
        userService.delete(currentUser);
        return ResponseEntity.ok().build();
    }
}
