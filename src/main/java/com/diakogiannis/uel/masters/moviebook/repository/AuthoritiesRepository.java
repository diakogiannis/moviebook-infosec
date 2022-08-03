package com.diakogiannis.uel.masters.moviebook.repository;

import com.diakogiannis.uel.masters.moviebook.model.entity.users.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthoritiesRepository extends JpaRepository<Authorities, String> {
}
