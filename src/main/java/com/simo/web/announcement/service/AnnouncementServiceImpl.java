package com.simo.web.announcement.service;

import com.simo.web.announcement.model.AnnouncementRegisterDTO;
import com.simo.web.announcement.model.AnnouncementEntity;
import com.simo.web.announcement.model.AnnouncementMapper;
import com.simo.web.announcement.repository.AnnouncementRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Override
    public List<AnnouncementRegisterDTO> findAll(){
        return announcementRepository
                .findAll()
                .stream()
                .map(AnnouncementMapper.INSTANCE::mapAnnouncementEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Scheduled(cron = "0 0 22 * * ?")
    public void cleanUpOldAnnouncements(){
        Instant endTime = Instant.now().minus(7, ChronoUnit.DAYS);
        announcementRepository.deleteByUpdatedOnBefore(endTime);
    }

    @Override
    public void createOrUpdateAnnouncement(AnnouncementRegisterDTO announcementRegisterDTO){
        AnnouncementEntity announcementEntity =
                AnnouncementMapper.INSTANCE.mapAnnouncementDtoToEntity(announcementRegisterDTO);

        if (announcementEntity.getCreatedOn() == null){
            announcementEntity.setCreatedOn(Instant.now());
        }
        announcementEntity.setUpdatedOn(Instant.now());

        announcementRepository.save(announcementEntity);
    }

    @Override
    public void delete(Long announcementId){
        announcementRepository.deleteById(announcementId);
    }
}
