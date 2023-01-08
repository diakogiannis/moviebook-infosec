package com.diakogiannis.psarros.propertyregistry.repository;

import com.diakogiannis.psarros.propertyregistry.model.entity.users.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthoritiesRepository extends JpaRepository<Authorities, String> {
    @Modifying(clearAutomatically = true)
    @Query("update Authorities a set a.authority=:authority where a.username=:username")
    int updateAuthority(@Param("authority") String authority, @Param("username") String username);
}
