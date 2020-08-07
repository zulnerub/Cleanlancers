package com.simo.web.region.model;

import com.simo.web.common.model.BaseEntity;
import com.simo.web.task.model.TaskEntity;
import com.simo.web.user.model.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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

    @OneToMany(
            mappedBy = "region",
            cascade = CascadeType.ALL
    )
    private List<UserEntity> users;



}
