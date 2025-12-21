package org.fathand.identity_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ErrorResponse> handlingSuperException(Exception exception) {
        System.out.println(exception.toString());

        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
        ErrorResponse response = new ErrorResponse();
        response.setStatusCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        response.setTitle(errorCode.getTitle());

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(value = ApplicationException.class)
    ResponseEntity<ErrorResponse> handlingApplicationException(ApplicationException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        int code = errorCode.getCode();

        ErrorResponse response = new ErrorResponse();
        response.setStatusCode(code);
        response.setMessage(errorCode.getMessage());
        response.setTitle(errorCode.getTitle());

        return ResponseEntity.status(code).body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ErrorCode errorCode = ErrorCode.VALIDATION_EXCEPTION;

        ErrorResponse response = new ErrorResponse();
        response.setStatusCode(errorCode.getCode());
        response.setTitle(errorCode.getTitle());
        response.setMessage(errorCode.getMessage());

        Map<String, String[]> errors = getValidationError(exception);
        List<ErrorDetail> errorDetails = errors.entrySet().stream()
                        .map(e -> new ErrorDetail(
                                e.getKey(),
                                e.getValue()
                        ))
                        .toList();
        response.setErrors(errorDetails);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    private Map<String, String[]> getValidationError(MethodArgumentNotValidException exception) {
        Map<String, String[]> errors;
        errors = exception.getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(
                                FieldError::getDefaultMessage,
                                Collectors.collectingAndThen(
                                        Collectors.toSet(),
                                        s -> s.toArray(String[]::new)
                                )
                        )
                ));
        return errors;
    }
}
