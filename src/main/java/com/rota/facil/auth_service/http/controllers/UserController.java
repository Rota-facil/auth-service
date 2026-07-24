package com.rota.facil.auth_service.http.controllers;

import com.rota.facil.auth_service.business.user.*;
import com.rota.facil.auth_service.http.dto.request.user.*;
import com.rota.facil.auth_service.http.dto.response.user.AccessTokenResponseDTO;
import com.rota.facil.auth_service.http.dto.response.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final CreateUserUseCase createUserUseCase;
    private final CreateDriverUseCase createDriverUseCase;
    private final CreateUserOfPrefectureUseCase createUserOfPrefectureUseCase;
    private final LoginUseCase loginUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final FetchUserUseCase fetchUserUseCase;
    private final ListStudentsUseCase listStudentsUseCase;
    private final CompleteGoogleRegistrationUseCase completeGoogleRegistrationUseCase;
    private final LogoutUseCase logoutUseCase;
    private final ChangePrefectureUserCase changePrefectureUserCase;
    private final DeactivateUserUseCase deactivateUserUseCase;
    private final DeactivateDriverUseCase deactivateDriverUseCase;
    private final UpdateDriverUseCase updateDriverUseCase;

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal CurrentUser currentUser) {
        logoutUseCase.execute(currentUser);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/user/prefecture/{prefectureId}/change")
    public ResponseEntity<Void> changePrefecture(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable UUID prefectureId) {
        changePrefectureUserCase.execute(currentUser, prefectureId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/driver/register")
    public ResponseEntity<AccessTokenResponseDTO> createDriverAccount(
            @Valid @RequestBody CreateUserAccountRequestDTO request,
            @AuthenticationPrincipal CurrentUser admin
    ) {
        return ResponseEntity.ok(createDriverUseCase.execute(request, admin));
    }

    @DeleteMapping("/driver/{driverId}/delete")
    public ResponseEntity<Void> deleteDriver(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID driverId
    ) {
        deactivateDriverUseCase.execute(currentUser, driverId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/driver/{driverId}/update")
    public ResponseEntity<Void> updateDriver(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID driverId,
            @RequestBody UpdateDriverRequestDTO request
    ) {
        updateDriverUseCase.execute(driverId, currentUser, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/prefecture/register")
    public ResponseEntity<AccessTokenResponseDTO> createPrefectureAccount(
            @Valid @RequestBody CreateUserAccountRequestDTO request,
            @AuthenticationPrincipal CurrentUser admin
            ) {
        return ResponseEntity.ok(createUserOfPrefectureUseCase.execute(request, admin));
    }

    @PostMapping("/google/complete-registration")
    public ResponseEntity<AccessTokenResponseDTO> completeGoogleRegistration(
            @RequestBody CompleteGoogleRegistrationRequestDTO request,
            @RequestParam String pendingToken
    ) {
        return ResponseEntity.ok(completeGoogleRegistrationUseCase.execute(request, UUID.fromString(pendingToken)));
    }

    @PostMapping("/register")
    public ResponseEntity<AccessTokenResponseDTO> createAccount(@Valid  @RequestBody CreateAccountRequestDTO request) {
        return ResponseEntity.ok(createUserUseCase.execute(request));
    }

    @PostMapping("/user/login")
    public ResponseEntity<AccessTokenResponseDTO> login (@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(loginUseCase.execute(request));
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponseDTO> update(
            @AuthenticationPrincipal CurrentUser currentUser,
            @Valid @RequestBody UpdateAccountRequestDTO request
    ) {
        return ResponseEntity.ok(updateUserUseCase.execute(request, currentUser));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> fetch(@AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(fetchUserUseCase.execute(currentUser));
    }

    @GetMapping("/students")
    public ResponseEntity<Page<UserResponseDTO>> listStudents(
            @ParameterObject @PageableDefault Pageable pageable,
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        return ResponseEntity.ok(listStudentsUseCase.execute(currentUser, pageable));
    }

    @PatchMapping("/deactivate")
    public ResponseEntity<Void> deactivateAccount(@AuthenticationPrincipal CurrentUser currentUser) {
        deactivateUserUseCase.execute(currentUser);
        return ResponseEntity.ok().build();
    }

}
