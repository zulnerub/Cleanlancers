package com.simo.web.region.model;

import com.simo.web.common.model.BaseEntity;
import com.simo.web.task.model.TaskEntity;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "regions")
public class RegionEntity extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(
            mappedBy = "region",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<TaskEntity> tasks;

    public RegionEntity() {
    }

    public String getName() {
        return name;
    }

    public RegionEntity setName(String name) {
        this.name = name;
        return this;
    }

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public RegionEntity setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
        return this;
    }
}
