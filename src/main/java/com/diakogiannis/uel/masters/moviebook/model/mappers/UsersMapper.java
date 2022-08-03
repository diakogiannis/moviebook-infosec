package com.diakogiannis.uel.masters.moviebook.model.mappers;

import com.diakogiannis.uel.masters.moviebook.model.dto.UsersDTO;
import com.diakogiannis.uel.masters.moviebook.model.entity.users.Users;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    UsersDTO toUsersDTO(Users users);

    List<UsersDTO> toUsersDTOs(List<Users> users);

    Users toUsers(UsersDTO usersDTO);
}
