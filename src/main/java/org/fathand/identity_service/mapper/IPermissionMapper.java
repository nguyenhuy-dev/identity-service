package org.fathand.identity_service.mapper;

import org.fathand.identity_service.dto.request.permission.PermissionRequest;
import org.fathand.identity_service.dto.response.permission.PermissionResponse;
import org.fathand.identity_service.entity.Permission;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IPermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
