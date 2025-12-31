package org.fathand.identity_service.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fathand.identity_service.dto.request.authentication.ChangePasswordRequest;
import org.fathand.identity_service.dto.request.authentication.LoginRequest;
import org.fathand.identity_service.dto.response.auth.LoginResponse;
import org.fathand.identity_service.entity.Role;
import org.fathand.identity_service.entity.User;
import org.fathand.identity_service.exception.ApplicationException;
import org.fathand.identity_service.exception.ErrorCode;
import org.fathand.identity_service.helper.PermissionHelper;
import org.fathand.identity_service.mapper.IAuthenticationMapper;
import org.fathand.identity_service.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    private static final Log log = LogFactory.getLog(AuthenticationService.class);
    IUserRepository userRepository;
    IAuthenticationMapper authenticationMapper;

    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY; // không lấy được cho static field

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ApplicationException(ErrorCode.UNAUTHENTICATED_LOGIN));

        if (!passwordEncoder.matches(request.password(), user.getPassword()))
            throw new ApplicationException(ErrorCode.UNAUTHENTICATED_LOGIN);

        var accessToken = generateAccessToken(user);

        LoginResponse loginResponse = authenticationMapper.toLoginResponse(user);
        loginResponse.setAccessToken(accessToken);

        return loginResponse;
    }

    private String generateAccessToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        int exp = 60 * 60 * 1000;
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("fathand.org")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plusMillis(exp).toEpochMilli()))
                .claim("scope", buildScope(user))
                .claim("user_id", user.getId())
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

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles()
                    .stream().map(Role::getName)
                    .forEach(stringJoiner::add);

        return stringJoiner.toString();
    }

    public void changePassword(String userId, ChangePasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (PermissionHelper.checkPermissionUserInfo(userId, authentication))
            throw new ApplicationException(ErrorCode.UNAUTHORIZED_ERROR);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword()))
            throw new ApplicationException(ErrorCode.OLD_PASSWORD_WRONG);

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }
}
