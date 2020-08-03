package com.simo.web.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentAddDTO {

    @NotBlank
    @Length(min = 2, message = "Comment must be long at least 2 characters")
    private String description;

    @NotNull
    private Long taskId;


}
