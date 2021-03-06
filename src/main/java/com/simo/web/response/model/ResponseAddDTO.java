package com.simo.web.response.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseAddDTO {

    @NotBlank
    @Length(min = 2, message = "Reply must be long at least 2 characters")
    private String responseDescription;

    @NotNull
    private Long commentId;

    private Long taskId;

}
