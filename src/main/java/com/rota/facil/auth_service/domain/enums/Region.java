package com.rota.facil.auth_service.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Region {
    NORTH("Norte"),
    SOUTH("Sul"),
    SOUTHEAST("Sudeste"),
    WEST("Oeste"),
    EAST("Leste"),
    NORTH_EAST("Nordeste"),
    WEST_CENTER("Centro-Oeste");

    private final String description;
}
