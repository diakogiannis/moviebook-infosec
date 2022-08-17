package com.diakogiannis.uel.masters.moviebook.service;

import com.diakogiannis.uel.masters.moviebook.enums.RolesEnum;
import com.diakogiannis.uel.masters.moviebook.exceptions.UserExistsException;
import com.diakogiannis.uel.masters.moviebook.model.dto.UserDetailsDTO;
import com.diakogiannis.uel.masters.moviebook.model.entity.users.Authorities;
import com.diakogiannis.uel.masters.moviebook.model.entity.users.Users;
import com.diakogiannis.uel.masters.moviebook.repository.AuthoritiesRepository;
import com.diakogiannis.uel.masters.moviebook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthoritiesRepository authoritiesRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager em;

    /**
     * @param users
     * @return
     * @throws UserExistsException
     */
    @Override
    @Transactional
    public Users registerUser(Users users) throws UserExistsException {

        if (userRepository.existsByUsername(users.getUsername())) {
            throw new UserExistsException();
        } else {
            users.setPassword(passwordEncoder.encode(users.getPassword()));
            users.setEnabled(Boolean.TRUE);
            users.setPublicIdentifier(UUID.randomUUID().toString());
            userRepository.save(users);
            authoritiesRepository.save(new Authorities(users.getUsername(), RolesEnum.ROLE_USER.toString()));
            return users;
        }
    }

    /**
     * @param username
     * @return
     */
    @Override
    public UserDetailsDTO getUserDetails(String username) {
        Users user = findUserByUsername(username);
        return new UserDetailsDTO(user.getUserId(), user.getUsername(), user.getFirstname(), user.getLastname());
    }

    /**
     * @param username
     * @return
     */
    @Override
    public Users findUserByUsername(String username) {
        return userRepository.findByUsername(username);

    }

}
