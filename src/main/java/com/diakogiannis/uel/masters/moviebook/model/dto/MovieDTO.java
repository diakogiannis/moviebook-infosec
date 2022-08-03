package com.diakogiannis.uel.masters.moviebook.model.dto;

import lombok.*;

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
