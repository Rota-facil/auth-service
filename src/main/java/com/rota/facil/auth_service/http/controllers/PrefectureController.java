package com.rota.facil.auth_service.http.controllers;

import com.rota.facil.auth_service.business.PrefectureService;
import com.rota.facil.auth_service.http.dto.request.prefecture.CreatePrefectureRequestDTO;
import com.rota.facil.auth_service.http.dto.request.prefecture.UpdatePrefectureRequestDTO;
import com.rota.facil.auth_service.http.dto.request.user.CurrentUser;
import com.rota.facil.auth_service.http.dto.response.prefecture.CreatePrefectureResponseDTO;
import com.rota.facil.auth_service.http.dto.response.prefecture.PrefectureResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{prefectureId}")
    public ResponseEntity<PrefectureResponseDTO> fetchPrefecture(@PathVariable UUID prefectureId) {
        return ResponseEntity.ok(prefectureService.fetch(prefectureId));
    }

    @GetMapping
    public ResponseEntity<List<PrefectureResponseDTO>> listPrefectures() {
        return ResponseEntity.ok(prefectureService.list());
    }

    @PutMapping("/{prefectureId}")
    public ResponseEntity<PrefectureResponseDTO> updatePrefecture(
            @PathVariable UUID prefectureId,
            @RequestBody UpdatePrefectureRequestDTO request,
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        return ResponseEntity.ok(prefectureService.update(prefectureId, request, currentUser));
    }

    @DeleteMapping("/{prefectureId}")
    public ResponseEntity<Void> deletePrefecture(@PathVariable UUID prefectureId, @AuthenticationPrincipal CurrentUser currentUser) {
        prefectureService.delete(prefectureId, currentUser);
        return ResponseEntity.ok().build();
    }
}
