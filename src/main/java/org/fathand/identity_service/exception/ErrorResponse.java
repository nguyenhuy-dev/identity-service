package org.fathand.identity_service.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private int statusCode;
    private String message;
    private String title;

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<ErrorDetail> errors;
}
