package org.fathand.identity_service.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class ErrorResponse {
    private int statusCode;
    private String message;
    private String title;

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<ErrorDetail> errors;

    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetail> errors) {
        this.errors = errors;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
