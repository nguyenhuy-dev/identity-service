package org.fathand.identity_service.dto.request.role;

import java.util.Set;

public record RoleRequest(String name, String description, Set<String> permissions) {
}
