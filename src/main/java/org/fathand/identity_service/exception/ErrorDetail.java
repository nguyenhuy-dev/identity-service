package org.fathand.identity_service.exception;

public record ErrorDetail(String fieldError, String[] messageErrors) {}
