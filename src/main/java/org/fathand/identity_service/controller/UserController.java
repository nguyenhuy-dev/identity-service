package org.fathand.identity_service.controller;

import jakarta.validation.Valid;
import org.fathand.identity_service.dto.request.ApiResponse;
import org.fathand.identity_service.dto.request.UserCreatedRequest;
import org.fathand.identity_service.dto.request.UserUpdatedRequest;
import org.fathand.identity_service.entity.User;
import org.fathand.identity_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreatedRequest request) throws Exception {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        User user = userService.createUser(request);
        apiResponse.setStatusCode(201);
        apiResponse.setData(user);

        return  apiResponse;
    }

    @GetMapping
    List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    User getUser(@PathVariable("userId") String userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    User updateUser(@PathVariable("userId") String userId, @RequestBody @Valid UserUpdatedRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return "User has been deleted successfully!";
    }
}
