package com.simo.web.task.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchTaskDTO {

    private String nameLike;
    private String clientFirstName;
    private String clientSecondName;
}
