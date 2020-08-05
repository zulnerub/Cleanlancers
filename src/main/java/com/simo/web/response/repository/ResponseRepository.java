package com.simo.web.response.repository;

import com.simo.web.response.model.ResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends JpaRepository<ResponseEntity, Long> {
}
