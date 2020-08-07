package com.simo.web.announcement.model;

import com.simo.web.common.model.BaseEntity;
import com.simo.web.user.model.RoleEntity;
import com.simo.web.user.model.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "announcements")
public class AnnouncementEntity extends BaseEntity {

    @NotNull
    @Column
    private Instant createdOn;

    @NotNull
    @Column
    private Instant updatedOn;

    @NotNull
    @Column
    private String title;

    @NotNull
    @Column
    private String description;

    @NotNull
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
