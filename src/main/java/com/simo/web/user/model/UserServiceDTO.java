package com.simo.web.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simo.web.announcement.model.AnnouncementEntity;
import com.simo.web.comment.model.CommentEntity;
import com.simo.web.region.model.RegionEntity;
import com.simo.web.response.model.ResponseEntity;
import com.simo.web.task.model.TaskEntity;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserServiceDTO {

    private Long id;
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private List<RoleEntity> roles;
    private List<CommentEntity> comments;
    private List<ResponseEntity> responses;
    private List<TaskEntity> cleanerTasks;
    private List<TaskEntity> clientTasks;
    private List<AnnouncementEntity> announcements;
    private RegionEntity region;


}
