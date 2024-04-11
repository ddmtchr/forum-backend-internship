package com.ddmtchr.forumbackendinternship.security.service;

import com.ddmtchr.forumbackendinternship.database.entities.Role;
import com.ddmtchr.forumbackendinternship.database.entities.Roles;
import com.ddmtchr.forumbackendinternship.database.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

    public Set<Role> fillRoles(Set<String> rolesString) {
        Set<Role> roles = new HashSet<>();
        if (rolesString == null || rolesString.isEmpty()) {
            Role postsRole = findRoleByName(Roles.ROLE_USER); // by default
            roles.add(postsRole);
        } else {
            rolesString.forEach((role) -> {
                String withPrefix = "ROLE_" + role;
                Roles eRole = Roles.valueOf(withPrefix);
                Role userRole = findRoleByName(eRole);
                roles.add(userRole);
            });
        }
        return roles;
    }
}
