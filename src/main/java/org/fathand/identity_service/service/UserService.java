package org.fathand.identity_service.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.fathand.identity_service.dto.request.user.UserCreatedRequest;
import org.fathand.identity_service.dto.request.user.UserUpdatedRequest;
import org.fathand.identity_service.dto.response.user.UserCreatedResponse;
import org.fathand.identity_service.dto.response.user.UserGetListResponse;
import org.fathand.identity_service.dto.response.user.UserGetResponse;
import org.fathand.identity_service.dto.response.user.UserUpdatedResponse;
import org.fathand.identity_service.entity.User;
import org.fathand.identity_service.enums.Role;
import org.fathand.identity_service.exception.ApplicationException;
import org.fathand.identity_service.exception.ErrorCode;
import org.fathand.identity_service.mapper.IUserMapper;
import org.fathand.identity_service.repository.IUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    IUserRepository userRepository;
    IUserMapper userMapper;

    PasswordEncoder passwordEncoder;

    public UserCreatedResponse createUser(UserCreatedRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new ApplicationException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // default role is User
        Set<String> roles = new HashSet<>();
        roles.add(Role.User.name());
        user.setRoles(roles);

        User userCreated = userRepository.save(user);

        return userMapper.toUserCreatedResponse(userCreated);
    }

    public List<UserGetListResponse> getUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toUserGetListResponseList(users);
    }

    public UserGetResponse getUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toUserGetResponse(user);
    }

    public UserUpdatedResponse updateUser(String userId, UserUpdatedRequest request) {
        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        userMapper.updateUser(user, request);
        User userUpdated = userRepository.save(user);

        return userMapper.toUserUpdatedResponse(userUpdated);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
