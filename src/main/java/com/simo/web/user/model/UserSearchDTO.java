package com.simo.web.user.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSearchDTO {

    private String email;
    private String firstName;
    private String lastName;
    private int numberOfTasks;
    private String workArea;

}
