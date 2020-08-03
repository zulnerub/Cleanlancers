package com.simo.web.announcement.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnnouncementDTO {

    private Long id;

    private Instant createdOn;

    private Instant updatedOn;

    @NotBlank
    private String title;

    @NotBlank
    @Size(min = 10, message = "Description should be at least 10 characters long.")
    private String description;
}
