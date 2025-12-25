package org.fathand.identity_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error system", "Server Error"),
    VALIDATION_EXCEPTION(400, "Validation failed", "Validation failed"),

    USER_EXISTED(400, "User existed", "Bad Request"),
    USER_NOT_FOUND(404, "User not found", "Not Found"),

    UNAUTHENTICATED_LOGIN(401, "Login failed! 'username' or 'password' is not correct", "Unauthenticated"),
    UNAUTHENTICATED_ERROR(401, "Authentication error", "Unauthenticated"),
    UNAUTHORIZED_ERROR(HttpStatus.FORBIDDEN.value(), "User cannot have permission to reach this feature", "Unauthorized"),

    OLD_PASSWORD_WRONG(400, "Old password is wrong", "Bad Request");
    ;

    ErrorCode(int code, String message, String title) {
        this.code = code;
        this.message = message;
        this.title = title;
    }

    private final int code;
    private final String message;
    private final String title;
}
