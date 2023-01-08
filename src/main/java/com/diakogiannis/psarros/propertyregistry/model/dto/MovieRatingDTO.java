package com.diakogiannis.psarros.propertyregistry.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class MovieRatingDTO {

    private String username;
    private Long movieId;
    private Boolean isLike;
}
