package com.simo.web.region.repository;

import com.simo.web.region.model.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<RegionEntity, Long> {


    Optional<RegionEntity> findByName(String name);
}
