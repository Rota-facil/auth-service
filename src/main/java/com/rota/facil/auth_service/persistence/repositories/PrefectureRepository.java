package com.rota.facil.auth_service.persistence.repositories;

import com.rota.facil.auth_service.persistence.entities.PrefectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrefectureRepository extends JpaRepository<PrefectureEntity, UUID> {
}
