package com.diakogiannis.psarros.propertyregistry.repository;

import com.diakogiannis.psarros.propertyregistry.model.entity.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, String> {
    Boolean existsByUsername(String username);

    //One username can only exist
    Users findByUsername(String username);
}
