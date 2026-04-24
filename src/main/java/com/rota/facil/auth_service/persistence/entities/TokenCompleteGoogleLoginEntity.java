package com.rota.facil.auth_service.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity
@Table(name = "token_complete_google_login_tb")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenCompleteGoogleLoginEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private UUID pendingToken;

    @Builder.Default
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
