package com.simo.web.announcement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simo.web.user.model.UserEntity;
import lombok.Data;

import java.time.Instant;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnnouncementServiceDto {

    private Long id;
    private Instant createdOn;
    private Instant updatedOn;
    private String title;
    private String description;
    private UserEntity user;
}
