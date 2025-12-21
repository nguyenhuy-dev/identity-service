package org.fathand.identity_service.dto.request.user;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.fathand.identity_service.validation.annotation.ValidDob;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreatedRequest {
    @Size(min = 3, message = "Username must be least 3 characters")
    String username;

    @Size(min = 8, message = "Password must be at least 8 characters")
    @Digits(integer = 10, fraction = 0, message = "Digits only and max 10 digits")
    String password;

    String firstName;

    String lastName;

    @ValidDob(message = "User must achieve at least age 18", min = 18)
    LocalDate dob;
}
