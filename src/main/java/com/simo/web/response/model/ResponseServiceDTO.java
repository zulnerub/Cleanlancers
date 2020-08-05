package com.simo.web.response.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simo.web.comment.model.CommentEntity;
import com.simo.web.user.model.UserEntity;
import lombok.Data;

import java.time.Instant;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseServiceDTO {

    private Long id;
    private String responseDescription;
    private Instant createdOn;
    private UserEntity author;
    private CommentEntity comment;
}
