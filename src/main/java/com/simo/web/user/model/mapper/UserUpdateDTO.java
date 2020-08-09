package com.simo.web.user.model.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUpdateDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String roles;
    private String region;
}
