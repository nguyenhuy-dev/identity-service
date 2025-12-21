package org.fathand.identity_service.service;

import org.fathand.identity_service.dto.request.UserCreatedRequest;
import org.fathand.identity_service.dto.request.UserUpdatedRequest;
import org.fathand.identity_service.entity.User;
import org.fathand.identity_service.exception.ApplicationException;
import org.fathand.identity_service.exception.ErrorCode;
import org.fathand.identity_service.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;

    public User createUser(UserCreatedRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new ApplicationException(ErrorCode.USER_EXISTED);

        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());

        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
    }

    public User updateUser(String userId, UserUpdatedRequest request) {
        User user = getUser(userId);

        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());

        return userRepository.save(user);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
