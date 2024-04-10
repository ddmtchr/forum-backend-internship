package com.ddmtchr.forumbackendinternship.security.service;

import com.ddmtchr.forumbackendinternship.database.entities.Role;
import com.ddmtchr.forumbackendinternship.database.entities.Roles;
import com.ddmtchr.forumbackendinternship.database.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findRoleByName(Roles name) {
        Optional<Role> role = roleRepository.findByName(name);
        if (role.isEmpty()) {
            Role newRole = new Role();
            newRole.setName(name);
            return addRole(newRole);
        }
        return role.get();
    }

    public Role addRole(Role role) {
        return roleRepository.save(role);
    }
}
