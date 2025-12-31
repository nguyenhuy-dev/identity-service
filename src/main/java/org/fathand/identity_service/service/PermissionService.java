package org.fathand.identity_service.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.fathand.identity_service.dto.request.permission.PermissionRequest;
import org.fathand.identity_service.dto.response.permission.PermissionResponse;
import org.fathand.identity_service.entity.Permission;
import org.fathand.identity_service.mapper.IPermissionMapper;
import org.fathand.identity_service.repository.IPermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    IPermissionRepository permissionRepository;
    IPermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);

        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permissionName) {
        permissionRepository.deleteById(permissionName);
    }
}
