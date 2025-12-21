package org.fathand.identity_service.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.fathand.identity_service.dto.request.authentication.LoginRequest;
import org.fathand.identity_service.dto.response.auth.LoginResponse;
import org.fathand.identity_service.entity.User;
import org.fathand.identity_service.exception.ApplicationException;
import org.fathand.identity_service.exception.ErrorCode;
import org.fathand.identity_service.mapper.IAuthenticationMapper;
import org.fathand.identity_service.repository.IUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    IUserRepository userRepository;
    IAuthenticationMapper authenticationMapper;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ApplicationException(ErrorCode.UNAUTHORIZED_LOGIN));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (!passwordEncoder.matches(request.password(), user.getPassword()))
            throw new ApplicationException(ErrorCode.UNAUTHORIZED_LOGIN);

        return authenticationMapper.toLoginResponse(user);
    }
}
