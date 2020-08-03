package com.simo.web.comment.repository;

import com.simo.web.comment.model.ResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends JpaRepository<ResponseEntity, Long> {
}
