package org.fathand.identity_service.dto.request.authentication;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotEmpty(message = "Must not be empty")
        @Size(min = 3, message = "Username must be least 3 characters")
        @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Username only contains letter or digit")
        String username,

        @NotEmpty(message = "Must not be empty")
        @Size(min = 8, message = "Password must be at least 8 characters")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
                message = "Password must contain at least one letter and one digit")
        String password) {
}
