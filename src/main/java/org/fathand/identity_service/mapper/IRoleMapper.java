package org.fathand.identity_service.mapper;

import org.fathand.identity_service.dto.request.role.RoleRequest;
import org.fathand.identity_service.dto.response.role.RoleResponse;
import org.fathand.identity_service.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IRoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    @Mapping(target = "permissions", ignore = true)
    RoleResponse toRoleResponse(Role role);
}
