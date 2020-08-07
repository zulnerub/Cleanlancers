package com.simo.web.announcement.service;

import com.simo.web.announcement.model.AnnouncementEntity;
import com.simo.web.announcement.model.AnnouncementRegisterDTO;
import com.simo.web.announcement.model.AnnouncementServiceDto;

import java.util.List;
import java.util.Optional;


public interface AnnouncementService {
    List<AnnouncementRegisterDTO> findAll();

    void cleanUpOldAnnouncements();

    void createOrUpdateAnnouncement(AnnouncementRegisterDTO announcementRegisterDTO);

    void delete(Long announcementId);

    List<AnnouncementEntity> findAllByUserEmail(String email);
}
