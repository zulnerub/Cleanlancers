package com.simo.web.comment.model;

import com.simo.web.common.model.BaseEntity;
import com.simo.web.task.model.TaskEntity;
import com.simo.web.user.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity {


    @Column(name = "description", nullable = false)
    private String description;


    @NotNull
    @Column(name = "created_on")
    private Instant createdOn;

    @OneToMany(
            mappedBy = "comment",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<ResponseEntity> responses;

    @NotNull
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @NotNull
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "task_id")
    private TaskEntity task;

}
