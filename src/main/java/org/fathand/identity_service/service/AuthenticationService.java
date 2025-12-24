package org.fathand.identity_service.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fathand.identity_service.dto.request.authentication.ChangePasswordRequest;
import org.fathand.identity_service.dto.request.authentication.LoginRequest;
import org.fathand.identity_service.dto.response.auth.LoginResponse;
import org.fathand.identity_service.entity.User;
import org.fathand.identity_service.exception.ApplicationException;
import org.fathand.identity_service.exception.ErrorCode;
import org.fathand.identity_service.mapper.IAuthenticationMapper;
import org.fathand.identity_service.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    private static final Log log = LogFactory.getLog(AuthenticationService.class);
    IUserRepository userRepository;
    IAuthenticationMapper authenticationMapper;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY; // không lấy được cho static field

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ApplicationException(ErrorCode.UNAUTHORIZED_LOGIN));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (!passwordEncoder.matches(request.password(), user.getPassword()))
            throw new ApplicationException(ErrorCode.UNAUTHORIZED_LOGIN);

        var accessToken = generateAccessToken(request.username());

        LoginResponse loginResponse = authenticationMapper.toLoginResponse(user);
        loginResponse.setAccessToken(accessToken);

        return loginResponse;
    }

    private String generateAccessToken(String username) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        int exp = 60 * 60 * 1000;
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("fathand.org")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plusMillis(exp).toEpochMilli()))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create access token", e);
            throw new RuntimeException(e);
        }
    }

    public void changePassword(String userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword()))
            throw new ApplicationException(ErrorCode.OLD_PASSWORD_WRONG);

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }
}
