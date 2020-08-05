package com.simo.web.response.model;

import com.simo.web.comment.model.CommentEntity;
import com.simo.web.common.model.BaseEntity;
import com.simo.web.user.model.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "responses")
public class ResponseEntity extends BaseEntity {

    @Column(name = "response_description", nullable = false)
    private String responseDescription;

    @NotNull
    @Column(name = "created_on")
    private Instant createdOn;

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
    @JoinColumn(name = "comment_id")
    private CommentEntity comment;

    public ResponseEntity() {
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public ResponseEntity setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
        return this;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public ResponseEntity setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public ResponseEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public CommentEntity getComment() {
        return comment;
    }

    public ResponseEntity setComment(CommentEntity comment) {
        this.comment = comment;
        return this;
    }
}
