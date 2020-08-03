package com.simo.web.announcement;

import com.simo.web.announcement.model.AnnouncementDTO;
import com.simo.web.announcement.model.AnnouncementEntity;
import com.simo.web.announcement.model.AnnouncementMapper;
import com.simo.web.announcement.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public List<AnnouncementDTO> findAll(){
        return announcementRepository
                .findAll()
                .stream()
                .map(AnnouncementMapper.INSTANCE::mapAnnouncementEntityToDto)
                .collect(Collectors.toList());
    }

    public void cleanUpOldAnnouncements(){
        Instant endTime = Instant.now().minus(7, ChronoUnit.DAYS);
        announcementRepository.deleteByUpdatedOnBefore(endTime);
    }

    public void createOrUpdateAnnouncement(AnnouncementDTO announcementDTO){
        AnnouncementEntity announcementEntity =
                AnnouncementMapper.INSTANCE.mapAnnouncementDtoToEntity(announcementDTO);

        if (announcementEntity.getCreatedOn() == null){
            announcementEntity.setCreatedOn(Instant.now());
        }
        announcementEntity.setUpdatedOn(Instant.now());

        announcementRepository.save(announcementEntity);
    }

    public void delete(Long announcementId){
        announcementRepository.deleteById(announcementId);
    }
}
