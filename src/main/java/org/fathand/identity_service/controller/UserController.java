package org.fathand.identity_service.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.fathand.identity_service.dto.request.user.UserCreatedRequest;
import org.fathand.identity_service.dto.request.user.UserUpdatedRequest;
import org.fathand.identity_service.dto.response.ApiResponse;
import org.fathand.identity_service.dto.response.user.UserCreatedResponse;
import org.fathand.identity_service.dto.response.user.UserGetListResponse;
import org.fathand.identity_service.dto.response.user.UserGetResponse;
import org.fathand.identity_service.dto.response.user.UserUpdatedResponse;
import org.fathand.identity_service.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<UserCreatedResponse> createUser(@RequestBody @Valid UserCreatedRequest request) {
        ApiResponse<UserCreatedResponse> apiResponse = new ApiResponse<>();
        UserCreatedResponse userResponse = userService.createUser(request);
        apiResponse.setStatusCode(201);
        apiResponse.setData(userResponse);

        return  apiResponse;
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping
    ApiResponse<List<UserGetListResponse>> getUsers() {
        List<UserGetListResponse> userResponses = userService.getUsers();

        ApiResponse<List<UserGetListResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setStatusCode(200);
        apiResponse.setData(userResponses);
        return apiResponse;
    }

    @GetMapping("/{userId}")
    ApiResponse<UserGetResponse> getUser(@PathVariable("userId") String userId) {
        UserGetResponse user = userService.getUser(userId);

        ApiResponse<UserGetResponse> apiResponse = new ApiResponse<>();
        apiResponse.setStatusCode(200);
        apiResponse.setData(user);

        return apiResponse;
    }

    @PutMapping("/{userId}")
    ApiResponse<UserUpdatedResponse> updateUser(@PathVariable("userId") @NotEmpty String userId, @RequestBody @Valid UserUpdatedRequest request) {
        UserUpdatedResponse userResponse = userService.updateUser(userId, request);

        ApiResponse<UserUpdatedResponse> apiResponse = new ApiResponse<>();
        apiResponse.setStatusCode(200);
        apiResponse.setData(userResponse);

        return apiResponse;
    }

    @DeleteMapping("/{userId}")
    ApiResponse<?> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);

        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setStatusCode(200);
        apiResponse.setMessage("User has been deleted successfully!");

        return apiResponse;
    }

    @GetMapping("/my-info")
    ApiResponse<UserGetResponse> getMyInfo() {
        UserGetResponse userResponse = userService.getMyInfo();

        ApiResponse<UserGetResponse> response = new ApiResponse<>();
        response.setStatusCode(200);
        response.setData(userResponse);

        return response;
    }
}
