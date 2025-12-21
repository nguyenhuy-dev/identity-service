package org.fathand.identity_service.mapper;

import org.fathand.identity_service.dto.request.user.UserCreatedRequest;
import org.fathand.identity_service.dto.request.user.UserUpdatedRequest;
import org.fathand.identity_service.dto.response.user.UserCreatedResponse;
import org.fathand.identity_service.dto.response.user.UserGetListResponse;
import org.fathand.identity_service.dto.response.user.UserGetResponse;
import org.fathand.identity_service.dto.response.user.UserUpdatedResponse;
import org.fathand.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    User toUser(UserCreatedRequest request);
    void updateUser(@MappingTarget User user, UserUpdatedRequest request);

    UserGetResponse toUserGetResponse(User user);
    UserCreatedResponse toUserCreatedResponse(User user);
    UserUpdatedResponse toUserUpdatedResponse(User user);
    List<UserGetListResponse> toUserGetListResponseList(List<User> users);
}
