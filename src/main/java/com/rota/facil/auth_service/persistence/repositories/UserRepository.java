package com.rota.facil.auth_service.persistence.repositories;

import com.rota.facil.auth_service.persistence.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        AND u.active IS TRUE
    """)
    Optional<UserEntity> findByEmail(@Param("email") String email);

    @Query("""
        SELECT u FROM UserEntity u
        INNER JOIN u.prefecture p
        WHERE u.role = com.rota.facil.auth_service.domain.enums.Role.STUDENT
        AND p.id = :prefectureId
        AND u.active IS TRUE
        ORDER BY u.name ASC
    """)
    Page<UserEntity> findAllStudentsByPrefectureId(@Param("prefectureId") UUID prefectureId, Pageable pageable);

    @Modifying
    @Query("""
        UPDATE UserEntity u SET
        u.completedTrips = u.completedTrips + 1
        WHERE u.id IN (:userIds)
        AND u.active IS TRUE
    """)
    void increaseTripCompletedByUserIds(@Param("userIds") List<UUID> userIds);

    @Modifying
    @Query("""
        UPDATE UserEntity u SET
        u.trips = u.trips + 1
        WHERE u.id IN (:userIds)
        AND u.active IS TRUE
    """)
    void increaseTripsByUserIds(@Param("userIds") List<UUID> userIds);

    @Modifying
    @Query("""
        UPDATE UserEntity u SET
        u.trips = CASE WHEN u.trips > 0 THEN u.trips - 1 ELSE 0 END
        WHERE u.id IN (:userIds)
        AND u.active IS TRUE
    """)
    void decreaseTripsByUserIds(@Param("userIds") List<UUID> userIds);

    @Query("""
        SELECT u FROM UserEntity u
        INNER JOIN u.prefecture p
        WHERE u.role = com.rota.facil.auth_service.domain.enums.Role.DRIVER
        AND u.id = :driverId
        AND p.id = :prefectureId
        AND u.active IS TRUE
    """)
    Optional<UserEntity> findDriverByIdAndPrefectureId(@Param("driverId") UUID driverId, @Param("prefectureId") UUID prefectureId);
}
