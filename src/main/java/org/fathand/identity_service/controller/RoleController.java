package org.fathand.identity_service.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.fathand.identity_service.dto.request.role.RoleRequest;
import org.fathand.identity_service.dto.response.ApiResponse;
import org.fathand.identity_service.dto.response.role.RoleResponse;
import org.fathand.identity_service.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .data(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<?> delete(@PathVariable String role) {
        roleService.delete(role);

        return ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Delete role successfully")
                .build();
    }

    @PutMapping
    ApiResponse<RoleResponse> update(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .data(roleService.update(request))
                .build();
    }
}
