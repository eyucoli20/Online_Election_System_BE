package com.electionSystem;

import com.electionSystem.userManager.role.Role;
import com.electionSystem.userManager.role.RoleRepository;
import com.electionSystem.userManager.user.UserRepository;
import com.electionSystem.userManager.user.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = "database", name = "seed", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class ApplicationRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    /**
     * Initializes the database with preloaded data upon application startup.
     */
    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            try {
                // Create and save roles
                List<Role> roles = createUserRole();
                roles = roleRepository.saveAll(roles);

                // Create and save user
                Users johnDoe = createUser(roles.get(0));
                userRepository.save(johnDoe);

                log.info("ApplicationRunner => Preloaded organization, roles and admin user");
            } catch (Exception ex) {
                log.error("ApplicationRunner Preloading Error: {}", ex.getMessage());
                throw new RuntimeException("ApplicationRunner Preloading Error ", ex);
            }
        };
    }

    private List<Role> createUserRole() {
        Role admin = new Role("ADMIN", "Full administrative access to manage and oversee the entire online election system");
        Role voter = new Role("USER", "Access to participate in voting");

        return List.of(admin, voter);
    }


    private Users createUser(Role role) {
        return Users.builder()
                .username("john@admin.com")
                .fullName("John Doe")
                .password(passwordEncoder.encode("123456"))
                .fullName("Joe Doe")
                .role(role)
                .build();
    }
}