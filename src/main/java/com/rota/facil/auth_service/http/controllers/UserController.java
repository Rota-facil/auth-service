package com.rota.facil.auth_service.http.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("teste");
    }
}
