package com.simo.web.comment.model;

import com.simo.web.common.model.BaseEntity;
import com.simo.web.response.model.ResponseEntity;
import com.simo.web.task.model.TaskEntity;
import com.simo.web.user.model.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

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
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "task_id")
    private TaskEntity task;

    public CommentEntity() {
    }

    public String getDescription() {
        return description;
    }

    public CommentEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public CommentEntity setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public List<ResponseEntity> getResponses() {
        return responses;
    }

    public CommentEntity setResponses(List<ResponseEntity> responses) {
        this.responses = responses;
        return this;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public CommentEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public TaskEntity getTask() {
        return task;
    }

    public CommentEntity setTask(TaskEntity task) {
        this.task = task;
        return this;
    }
}
