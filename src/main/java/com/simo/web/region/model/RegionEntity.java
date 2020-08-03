package com.simo.web.region.model;

import com.simo.web.common.model.BaseEntity;
import com.simo.web.task.model.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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


}
