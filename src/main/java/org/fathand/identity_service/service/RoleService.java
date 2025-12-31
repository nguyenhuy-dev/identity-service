package org.fathand.identity_service.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.fathand.identity_service.dto.request.role.RoleRequest;
import org.fathand.identity_service.dto.response.role.RoleResponse;
import org.fathand.identity_service.entity.Permission;
import org.fathand.identity_service.entity.Role;
import org.fathand.identity_service.mapper.IPermissionMapper;
import org.fathand.identity_service.mapper.IRoleMapper;
import org.fathand.identity_service.repository.IPermissionRepository;
import org.fathand.identity_service.repository.IRoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    IRoleRepository roleRepository;
    IRoleMapper roleMapper;
    IPermissionRepository permissionRepository;
    IPermissionMapper permissionMapper;

    public RoleResponse create(RoleRequest request) {
        Role role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.permissions());
        role.setPermissions(new HashSet<>(permissions));
        roleRepository.save(role);

        RoleResponse roleResponse = roleMapper.toRoleResponse(role);
        roleResponse.setPermissions(permissions.stream().map(permissionMapper::toPermissionResponse)
                .collect(Collectors.toSet()));

        return roleResponse;
    }

    public List<RoleResponse> getAll() {
        var roles = roleRepository.findAll();
        return roles.stream().map(r -> {
            RoleResponse roleResponse = roleMapper.toRoleResponse(r);
            roleResponse.setPermissions(r.getPermissions().stream().map(permissionMapper::toPermissionResponse)
                    .collect(Collectors.toSet()));
            return roleResponse;
        }).toList();
    }

    public void delete(String role) {
        roleRepository.deleteById(role);
    }

    public RoleResponse update(RoleRequest request) {
        Role role = roleMapper.toRole(request);
        List<Permission> permissions = permissionRepository.findAllById(request.permissions());
        role.setPermissions(new HashSet<>(permissions));
        roleRepository.save(role);

        RoleResponse roleResponse = roleMapper.toRoleResponse(role);
        roleResponse.setPermissions(permissions.stream()
                .map(permissionMapper::toPermissionResponse)
                .collect(Collectors.toSet()));

        return roleResponse;
    }
}
