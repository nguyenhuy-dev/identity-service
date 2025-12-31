package org.fathand.identity_service.dto.response.user;

import jakarta.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.fathand.identity_service.entity.Role;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserGetResponse {
    String id;
    String username;
    String firstName;
    String lastName;
    LocalDate dob;
    Set<Role> roles;
}
