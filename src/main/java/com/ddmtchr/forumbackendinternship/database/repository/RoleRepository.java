package com.ddmtchr.forumbackendinternship.database.repository;

import com.ddmtchr.forumbackendinternship.database.entities.Role;
import com.ddmtchr.forumbackendinternship.database.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Roles name);
}
