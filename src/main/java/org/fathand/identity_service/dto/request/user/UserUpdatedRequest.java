package org.fathand.identity_service.dto.request.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.fathand.identity_service.validation.annotation.ValidDate;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdatedRequest {
    String firstName;

    String lastName;

    @ValidDate(min = 18, message = "User must achieve at least age 18")
    LocalDate dob;
}
