package com.simo.web.task.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simo.web.comment.model.CommentEntity;
import com.simo.web.comment.model.CommentServiceDTO;
import com.simo.web.region.model.RegionEntity;
import com.simo.web.region.model.RegionServiceDTO;
import com.simo.web.user.model.UserEntity;
import com.simo.web.user.model.UserServiceModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskServiceDTO {

    private Long id;
    private String name;
    private UserServiceModel client;
    private UserServiceModel cleaner;
    private RegionEntity region;
    private List<CommentServiceDTO> comments;


}
