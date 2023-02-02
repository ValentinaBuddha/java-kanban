package com.yandex.app.exceptions;

public class StatusCodeException extends RuntimeException {

    public StatusCodeException() {
        super();
    }

    public StatusCodeException(String message) {
        super(message);
    }
}