package com.ddmtchr.forumbackendinternship;

import com.ddmtchr.forumbackendinternship.database.entities.User;
import com.ddmtchr.forumbackendinternship.security.service.RoleService;
import com.ddmtchr.forumbackendinternship.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserInitializer implements CommandLineRunner {
    private final PasswordEncoder encoder;
    private final UserDetailsServiceImpl userService;
    private final RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        User user = new User("qwerty", encoder.encode("123"));
        user.setRoles(roleService.fillRoles(Set.of("USER")));
        User admin = new User("admin", encoder.encode("admin"));
        admin.setRoles(roleService.fillRoles(Set.of("ADMIN")));

        userService.addUser(user);
        userService.addUser(admin);
    }
}
