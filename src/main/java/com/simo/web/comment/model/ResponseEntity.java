package com.simo.web.comment.model;

import com.simo.web.common.model.BaseEntity;
import com.simo.web.user.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "responses")
public class ResponseEntity extends BaseEntity {

    @Column(name = "description", nullable = false)
    private String description;

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


}
