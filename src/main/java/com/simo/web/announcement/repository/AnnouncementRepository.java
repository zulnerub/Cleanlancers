package com.simo.web.announcement.repository;

import com.simo.web.announcement.model.AnnouncementEntity;
import com.simo.web.announcement.model.AnnouncementServiceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementRepository  extends JpaRepository<AnnouncementEntity, Long> {

    void deleteByUpdatedOnBefore(Instant before);

    Optional<List<AnnouncementEntity>> findAllByUser_EmailOrderByCreatedOnDesc(String email);
}
