package org.fathand.identity_service.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.fathand.identity_service.entity.User;
import org.fathand.identity_service.enums.Role;
import org.fathand.identity_service.repository.IUserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(IUserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                var roles = new HashSet<String>();
                roles.add(Role.Admin.name());

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin12345"))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("User 'admin' has been created with default password: 'admin12345', please change it!!!");
            }
        };
    }
}
