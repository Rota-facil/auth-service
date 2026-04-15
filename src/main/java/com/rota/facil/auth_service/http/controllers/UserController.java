package com.rota.facil.auth_service.http.controllers;

import com.rota.facil.auth_service.business.UserService;
import com.rota.facil.auth_service.http.dto.request.CreateAccountRequestDTO;
import com.rota.facil.auth_service.http.dto.request.LoginRequestDTO;
import com.rota.facil.auth_service.http.dto.response.AccessTokenResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AccessTokenResponseDTO> createAccount(@Valid  @RequestBody CreateAccountRequestDTO request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/login")
    public ResponseEntity<AccessTokenResponseDTO> login (@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(userService.login(request));
    }
}
