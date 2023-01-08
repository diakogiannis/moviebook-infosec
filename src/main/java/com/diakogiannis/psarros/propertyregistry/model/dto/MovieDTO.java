package com.diakogiannis.psarros.propertyregistry.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class MovieDTO {

    private @NonNull @NotBlank(message = "Title is mandatory") String title;
    private @NonNull @NotBlank(message = "Description is mandatory") String description;
    private @NonNull String username;
    private Long likes;
    private Long hates;
    private LocalDateTime publicationDate;
    private String firstName;
    private String lastName;


}
