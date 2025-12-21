package org.fathand.identity_service.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import org.fathand.identity_service.validation.annotation.ValidDob;

import java.time.LocalDate;

public class UserCreatedRequest {
    @Size(min = 3, message = "Username must be least 3 characters")
    private String username;

    @Size(min = 8, message = "Password must be at least 8 characters")
    @Digits(integer = 10, fraction = 0, message = "Digits only and max 10 digits")
    private String password;

    private String firstName;

    private String lastName;

    @ValidDob(message = "User must achieve at least age 18", min = 18)
    private LocalDate dob;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}
