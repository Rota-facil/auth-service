package com.rota.facil.auth_service.http.controllers;

import com.rota.facil.auth_service.business.PrefectureService;
import com.rota.facil.auth_service.http.dto.request.prefecture.CreatePrefectureRequestDTO;
import com.rota.facil.auth_service.http.dto.request.user.CurrentUser;
import com.rota.facil.auth_service.http.dto.response.prefecture.CreatePrefectureResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prefecture")
@RequiredArgsConstructor
public class PrefectureController {
    private final PrefectureService prefectureService;

    @PostMapping
    public ResponseEntity<CreatePrefectureResponseDTO> createPrefecture(
            @Valid @RequestBody CreatePrefectureRequestDTO request,
            @AuthenticationPrincipal CurrentUser currentUser
            ) {
        return ResponseEntity.ok(prefectureService.register(request, currentUser));
    }
}
