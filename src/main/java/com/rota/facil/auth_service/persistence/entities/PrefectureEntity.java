package com.rota.facil.auth_service.persistence.entities;

import com.rota.facil.auth_service.domain.enums.Region;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Entity
@Table(name = "prefectures_tb")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrefectureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "prefecture_id")
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Region region;
}
