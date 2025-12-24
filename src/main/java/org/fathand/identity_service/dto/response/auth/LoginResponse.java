package org.fathand.identity_service.dto.response.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponse {
    String id;
    String username;
    String firstName;
    String lastName;
    String accessToken;
}
