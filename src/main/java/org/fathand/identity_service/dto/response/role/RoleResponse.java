package org.fathand.identity_service.dto.response.role;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.fathand.identity_service.dto.response.permission.PermissionResponse;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {
    String name;
    String description;
    Set<PermissionResponse> permissions;
}
