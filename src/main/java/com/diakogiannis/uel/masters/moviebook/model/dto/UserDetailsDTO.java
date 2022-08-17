package com.diakogiannis.uel.masters.moviebook.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class UserDetailsDTO implements Serializable {
    private @NonNull Long userId;
    private @NonNull String username;
    private @NonNull String firstname;
    private @NonNull String lastname;
}
