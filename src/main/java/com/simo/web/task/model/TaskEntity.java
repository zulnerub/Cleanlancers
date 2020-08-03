package com.simo.web.task.model;

import com.simo.web.comment.model.CommentEntity;
import com.simo.web.common.model.BaseEntity;
import com.simo.web.region.model.RegionEntity;
import com.simo.web.user.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Table(name = "tasks")
public class TaskEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "client_id")
    private UserEntity client;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "cleaner_id")
    private UserEntity cleaner;

    @NotNull
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "region_id")
    private RegionEntity region;

    @OneToMany(
            mappedBy = "task",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CommentEntity> comments;

    public TaskEntity() {
    }

    public String getName() {
        return name;
    }

    public TaskEntity setName(String name) {
        this.name = name;
        return this;
    }

    public UserEntity getClient() {
        return client;
    }

    public TaskEntity setClient(UserEntity client) {
        this.client = client;
        return this;
    }

    public UserEntity getCleaner() {
        return cleaner;
    }

    public TaskEntity setCleaner(UserEntity cleaner) {
        this.cleaner = cleaner;
        return this;
    }

    public RegionEntity getRegion() {
        return region;
    }

    public TaskEntity setRegion(RegionEntity region) {
        this.region = region;
        return this;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public TaskEntity setComments(List<CommentEntity> comments) {
        this.comments = comments;
        return this;
    }

    //    public void addComment(CommentEntity comment) {
//        comments.add(comment);
//        comment.setTask(this);
//    }
//
//    public void removeComment(CommentEntity comment) {
//        comments.remove(comment);
//        comment.setTask(null);
//    }

}
