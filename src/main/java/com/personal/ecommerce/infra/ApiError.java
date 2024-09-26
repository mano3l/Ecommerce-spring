package com.personal.ecommerce.infra;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Getter
@ToString
public class ApiError {

    private HttpStatus status;
    private String message;

    public ApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}