package org.fathand.identity_service.helper;

import jakarta.validation.constraints.NotNull;
import org.fathand.identity_service.enums.Role;
import org.fathand.identity_service.exception.ApplicationException;
import org.fathand.identity_service.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Objects;

public class PermissionHelper {
    public static boolean checkPermissionUserInfo(@NotNull String userId, Authentication authentication) {
        if (Objects.isNull(authentication))
            throw new ApplicationException(ErrorCode.UNAUTHENTICATED_ERROR);

        Jwt jwt = (Jwt) authentication.getPrincipal();
        if (Objects.isNull(jwt))
            throw new ApplicationException(ErrorCode.UNAUTHENTICATED_ERROR);

        String rolesString = jwt.getClaimAsString("scope");
        if (rolesString.contains(Role.Admin.name()))
            return false;

        String userIdJwt = jwt.getClaimAsString("user_id");
        return !userId.equals(userIdJwt);
    }
}
