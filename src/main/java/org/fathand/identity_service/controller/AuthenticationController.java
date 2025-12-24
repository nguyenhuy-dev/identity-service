package org.fathand.identity_service.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.fathand.identity_service.dto.request.authentication.ChangePasswordRequest;
import org.fathand.identity_service.dto.request.authentication.LoginRequest;
import org.fathand.identity_service.dto.response.ApiResponse;
import org.fathand.identity_service.dto.response.auth.LoginResponse;
import org.fathand.identity_service.service.AuthenticationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authService;

    @PostMapping("/sign-in")
    ApiResponse<LoginResponse> signIn(@RequestBody @Valid LoginRequest request) {
        LoginResponse loginResponse = authService.login(request);
        ApiResponse<LoginResponse> response = new ApiResponse<>();
        response.setStatusCode(200);
        response.setData(loginResponse);

        return response;
    }

    @PostMapping("/change-password/{userId}")
    ApiResponse<?> changePassword(@PathVariable("userId") String userId, @RequestBody @Valid ChangePasswordRequest request) {
        authService.changePassword(userId, request);
        ApiResponse<?> response = new ApiResponse<>();
        response.setStatusCode(201);
        response.setMessage("Change password successfully!");

        return response;
    }
}
