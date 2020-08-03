package com.simo.web.user.model;

import com.simo.web.comment.model.CommentEntity;
import com.simo.web.common.model.BaseEntity;
import com.simo.web.task.model.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<RoleEntity> roles;

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
    private List<CommentEntity> responses;

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

}
