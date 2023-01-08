package com.diakogiannis.psarros.propertyregistry.service;

import com.diakogiannis.psarros.propertyregistry.exceptions.UserExistsException;
import com.diakogiannis.psarros.propertyregistry.model.dto.UserDetailsDTO;
import com.diakogiannis.psarros.propertyregistry.model.entity.users.Users;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    @Transactional
    Users registerUser(Users users) throws UserExistsException;

    UserDetailsDTO getUserDetails(String username);

    Users findUserByUsername(String username);

    int updateAuthority(String authority, String username);
}
