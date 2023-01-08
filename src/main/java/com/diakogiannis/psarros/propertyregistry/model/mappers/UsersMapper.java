package com.diakogiannis.psarros.propertyregistry.model.mappers;

import com.diakogiannis.psarros.propertyregistry.model.dto.UsersDTO;
import com.diakogiannis.psarros.propertyregistry.model.entity.users.Users;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsersMapper {
    public UsersDTO toUsersDTO(Users users){
        if(users == null){
            return null;
        }
        return new UsersDTO(users.getUsername(), users.getPassword(), users.getFirstname(), users.getLastname());
    }

    public List<UsersDTO> toUsersDTOs(List<Users> users){
        List<UsersDTO> usersDTOS = new ArrayList<>();
        for (Users u : users) {
            usersDTOS.add(toUsersDTO(u));
        }
        return usersDTOS;
    }

    public Users toUsers(UsersDTO usersDTO){
        if (usersDTO == null){
            return null;
        }
        Users users = new Users();
        users.setUsername(usersDTO.getUsername());
        users.setPassword(usersDTO.getPassword());
        users.setFirstname(usersDTO.getFirstname());
        users.setLastname(usersDTO.getLastname());
        return users;
    }
}
