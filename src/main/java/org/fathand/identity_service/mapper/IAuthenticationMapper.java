package org.fathand.identity_service.mapper;

import org.fathand.identity_service.dto.response.auth.LoginResponse;
import org.fathand.identity_service.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAuthenticationMapper {
    LoginResponse toLoginResponse(User user);
}
