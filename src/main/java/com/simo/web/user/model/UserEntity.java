package com.simo.web.user.model;

import com.simo.web.announcement.model.AnnouncementEntity;
import com.simo.web.comment.model.CommentEntity;
import com.simo.web.common.model.BaseEntity;
import com.simo.web.region.model.RegionEntity;
import com.simo.web.response.model.ResponseEntity;
import com.simo.web.task.model.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String passwordHash;

    @Length(min = 2, max = 50, message = "First name length must be between 2 and 50 characters.")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Length(min = 2, max = 50, message = "Last name length must be between 2 and 50 characters.")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToMany(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER
    )
    private Set<RoleEntity> roles;

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CommentEntity> comments;

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ResponseEntity> responses;

    @OneToMany(
            mappedBy = "cleaner",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TaskEntity> cleanerTasks;

    @OneToMany(
            mappedBy = "client",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TaskEntity> clientTasks;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<AnnouncementEntity> announcements;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "region_id")
    private RegionEntity region;

}
