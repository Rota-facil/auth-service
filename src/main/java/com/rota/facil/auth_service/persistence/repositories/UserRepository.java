package com.rota.facil.auth_service.persistence.repositories;

import com.rota.facil.auth_service.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    @Query("""
        SELECT u FROM UserEntity u
        WHERE u.email = :email
    """)
    Optional<UserEntity> findByEmail(@Param("email") String email);

    @Modifying
    @Query("""
        UPDATE UserEntity u SET
        u.completedTrips = u.completedTrips + 1
        WHERE u.id IN (:userIds)
    """)
    void increaseTripCompletedByUserIds(@Param("userIds") List<UUID> userIds);
}
