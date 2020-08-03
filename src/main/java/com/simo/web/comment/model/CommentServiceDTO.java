package com.simo.web.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simo.web.task.model.TaskEntity;
import com.simo.web.task.model.TaskServiceDTO;
import com.simo.web.user.model.UserEntity;
import com.simo.web.user.model.UserServiceModel;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentServiceDTO {

    private Long id;
    private String description;
    private Instant createdOn;
    List<ResponseEntity> responses;
    private UserEntity author;
    private TaskEntity task;
}
