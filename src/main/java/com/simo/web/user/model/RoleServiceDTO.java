package com.simo.web.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleServiceDTO {

    private Long id;
    private String name;
}
