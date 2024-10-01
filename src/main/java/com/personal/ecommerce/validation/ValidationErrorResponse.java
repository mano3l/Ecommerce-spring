package com.personal.ecommerce.validation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ValidationErrorResponse {

    private String message;
    private List<String> details;

    public ValidationErrorResponse(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }

}
