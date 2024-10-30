package com.personal.ecommerce.infra;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ApiError {

    private final HttpStatus status;
    private final String message;

    public ApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}