package org.fathand.identity_service.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationException extends RuntimeException {
    public ApplicationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    private ErrorCode errorCode;

}
