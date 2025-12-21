package org.fathand.identity_service.dto.response.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserGetResponse {
    String id;
    String username;
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
}
