package org.fathand.identity_service.dto.request.authentication;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.fathand.identity_service.validation.annotation.FieldMatch;

@FieldMatch(first = "newPassword", second = "confirmedNewPassword", message = "New passwords do not match")
public record ChangePasswordRequest(
        @NotEmpty(message = "Must not be empty")
        @Size(min = 8, message = "Password must be at least 8 characters")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
                message = "Password must contain at least one letter and one digit")
        String oldPassword,

        @NotEmpty(message = "Must not be empty")
        @Size(min = 8, message = "Password must be at least 8 characters")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
                message = "Password must contain at least one letter and one digit")
        String newPassword,

        @NotEmpty(message = "Must not be empty")
        @Size(min = 8, message = "Password must be at least 8 characters")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
                message = "Password must contain at least one letter and one digit")
        String confirmedNewPassword) {
}
