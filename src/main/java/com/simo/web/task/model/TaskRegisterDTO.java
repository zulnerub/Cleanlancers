package com.simo.web.task.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskRegisterDTO {

    @NotBlank(message = "Please enter name of task.")
    private String name;

    @NotBlank(message = "Please choose a region")
    private String region;

    public String getName() {
        return name;
    }

    public TaskRegisterDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public TaskRegisterDTO setRegion(String region) {
        this.region = region;
        return this;
    }
}
