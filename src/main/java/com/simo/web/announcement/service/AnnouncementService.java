package com.simo.web.announcement.service;

import com.simo.web.announcement.model.AnnouncementRegisterDTO;

import java.util.List;


public interface AnnouncementService {
    List<AnnouncementRegisterDTO> findAll();

    void cleanUpOldAnnouncements();

    void createOrUpdateAnnouncement(AnnouncementRegisterDTO announcementRegisterDTO);

    void delete(Long announcementId);
}
