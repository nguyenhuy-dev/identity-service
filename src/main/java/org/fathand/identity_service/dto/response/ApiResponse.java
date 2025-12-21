package org.fathand.identity_service.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private int statusCode;
    private String message;
    private T data;
}
