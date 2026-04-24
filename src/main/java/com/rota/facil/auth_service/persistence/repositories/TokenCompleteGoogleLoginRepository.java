package com.rota.facil.auth_service.persistence.repositories;

import com.rota.facil.auth_service.persistence.entities.TokenCompleteGoogleLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface TokenCompleteGoogleLoginRepository extends JpaRepository<TokenCompleteGoogleLoginEntity, UUID> {
    @Query("""
        SELECT tcgl FROM TokenCompleteGoogleLoginEntity tcgl
        WHERE tcgl.pendingToken = :pendingToken
    """)
    Optional<TokenCompleteGoogleLoginEntity> findByToken(@Param("pendingToken") UUID pendingToken);

    @Transactional
    @Modifying
    @Query("""
        DELETE FROM TokenCompleteGoogleLoginEntity tcgl
        WHERE tcgl.pendingToken = :pendingToken
    """)
    void deleteByToken(@Param("pendingToken") UUID pendingToken);
}
