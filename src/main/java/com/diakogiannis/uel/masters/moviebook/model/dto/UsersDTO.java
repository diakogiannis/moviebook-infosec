package com.diakogiannis.uel.masters.moviebook.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class UsersDTO {
    private @NonNull @NotBlank(message = "Username is mandatory") @Size(max = 255, message = "Maximum size is 255") String username;
    private @NotBlank(message = "Password is mandatory") @Size(max = 255, message = "Maximum size is 255") String password;
    private @NonNull @NotBlank(message = "Firstname is mandatory") @Size(max = 255, message = "Maximum size is 255") String firstname;
    private @NonNull @NotBlank(message = "Lastname is mandatory") @Size(max = 255, message = "Maximum size is 255") String lastname;

}
