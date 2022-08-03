package com.diakogiannis.uel.masters.moviebook.service;

import com.diakogiannis.uel.masters.moviebook.exceptions.UserExistsException;
import com.diakogiannis.uel.masters.moviebook.model.dto.UserDetailsDTO;
import com.diakogiannis.uel.masters.moviebook.model.entity.users.Users;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    @Transactional
    Users registerUser(Users users) throws UserExistsException;

    UserDetailsDTO getUserDetails(String username);

    Users findUserByUsername(String username);
}
