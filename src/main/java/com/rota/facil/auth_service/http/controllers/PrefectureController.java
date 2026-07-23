package com.rota.facil.auth_service.http.controllers;

import com.rota.facil.auth_service.business.prefecture.*;
import com.rota.facil.auth_service.http.dto.request.prefecture.CreatePrefectureRequestDTO;
import com.rota.facil.auth_service.http.dto.request.prefecture.UpdatePrefectureRequestDTO;
import com.rota.facil.auth_service.http.dto.request.user.CurrentUser;
import com.rota.facil.auth_service.http.dto.response.prefecture.CreatePrefectureResponseDTO;
import com.rota.facil.auth_service.http.dto.response.prefecture.PrefectureResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/prefectures")
@RequiredArgsConstructor
public class PrefectureController {
    private final CreatePrefectureUseCase createPrefectureUseCase;
    private final FetchPrefectureUseCase fetchPrefectureUseCase;
    private final ListPrefectureUseCase listPrefectureUseCase;
    private final UpdatePrefectureUseCase updatePrefectureUseCase;
    private final DeletePrefectureUseCase deletePrefectureUseCase;

    @PostMapping
    public ResponseEntity<CreatePrefectureResponseDTO> createPrefecture(
            @Valid @RequestBody CreatePrefectureRequestDTO request,
            @AuthenticationPrincipal CurrentUser currentUser
            ) {
        return ResponseEntity.ok(createPrefectureUseCase.execute(request, currentUser));
    }

    @GetMapping("/{prefectureId}")
    public ResponseEntity<PrefectureResponseDTO> fetchPrefecture(@PathVariable UUID prefectureId) {
        return ResponseEntity.ok(fetchPrefectureUseCase.execute(prefectureId));
    }

    @GetMapping
    public ResponseEntity<List<PrefectureResponseDTO>> listPrefectures() {
        return ResponseEntity.ok(listPrefectureUseCase.execute());
    }

    @PutMapping("/{prefectureId}")
    public ResponseEntity<PrefectureResponseDTO> updatePrefecture(
            @PathVariable UUID prefectureId,
            @RequestBody UpdatePrefectureRequestDTO request,
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        return ResponseEntity.ok(updatePrefectureUseCase.execute(prefectureId, request, currentUser));
    }

    @DeleteMapping("/{prefectureId}")
    public ResponseEntity<Void> deletePrefecture(@PathVariable UUID prefectureId, @AuthenticationPrincipal CurrentUser currentUser) {
        deletePrefectureUseCase.execute(prefectureId, currentUser);
        return ResponseEntity.ok().build();
    }
}
