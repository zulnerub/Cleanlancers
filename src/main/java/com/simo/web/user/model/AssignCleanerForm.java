package com.simo.web.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.validation.constraints.Email;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AssignCleanerForm {
    private String taskRegion;
    private String assignedUsername;
    private Long taskId;

    @Email
    public String getAssignedUsername() {
        return assignedUsername;
    }

    public AssignCleanerForm setAssignedUsername(String assignedUsername) {
        this.assignedUsername = assignedUsername;
        return this;
    }

    public Long getTaskId() {
        return taskId;
    }

    public AssignCleanerForm setTaskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getTaskRegion() {
        return taskRegion;
    }

    public AssignCleanerForm setTaskRegion(String taskRegion) {
        this.taskRegion = taskRegion;
        return this;
    }
}
