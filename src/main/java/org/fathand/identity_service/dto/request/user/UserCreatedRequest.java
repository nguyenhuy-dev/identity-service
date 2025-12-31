package org.fathand.identity_service.dto.request.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.fathand.identity_service.entity.Role;
import org.fathand.identity_service.validation.annotation.ValidDate;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreatedRequest {
    @Size(min = 3, message = "Username must be least 3 characters")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Username only contains letter or digit")
    String username;

    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
            message = "Password must contain at least one letter and one digit")
    String password;

    String firstName;

    String lastName;

    @ValidDate(message = "User must achieve at least age 18", min = 18)
    LocalDate dob;

    Set<Role> roles;
}
