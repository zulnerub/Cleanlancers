package com.simo.web.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simo.web.user.model.UserEntity;
import com.simo.web.user.model.UserServiceModel;
import lombok.Data;

import java.time.Instant;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseServiceDTO {

    private Long id;
    private String description;
    private Instant createdOn;
    private UserEntity author;
    private CommentEntity comment;
}
