package com.simo.web.task.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simo.web.comment.model.CommentServiceDTO;
import com.simo.web.region.model.RegionEntity;
import com.simo.web.user.model.UserServiceDTO;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskServiceDTO {

    private Long id;
    private String name;
    private UserServiceDTO client;
    private UserServiceDTO cleaner;
    private RegionEntity region;
    private List<CommentServiceDTO> comments;


}
