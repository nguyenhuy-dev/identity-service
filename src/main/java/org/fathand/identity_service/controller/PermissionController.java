package org.fathand.identity_service.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.fathand.identity_service.dto.request.permission.PermissionRequest;
import org.fathand.identity_service.dto.response.ApiResponse;
import org.fathand.identity_service.dto.response.permission.PermissionResponse;
import org.fathand.identity_service.service.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .data(permissionService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permissionName}")
    ApiResponse<?> delete(@PathVariable("permissionName") String permissionName) {
        permissionService.delete(permissionName);
        return ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Delete permission successfully")
                .build();
    }
}
