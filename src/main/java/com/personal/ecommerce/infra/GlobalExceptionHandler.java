package com.personal.ecommerce.infra;

import com.personal.ecommerce.infra.exception.CategoryNotFoundException;
import com.personal.ecommerce.infra.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Arrays;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(HandlerMethodValidationException ex) {
        List<String> errors = ex.getAllErrors().stream().map(MessageSourceResolvable::getDefaultMessage).toList();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Validation Error");
        log.error("Validation error", ex);
        log.error(String.valueOf(errors));
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiError> handleProductNotFoundException(ProductNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Product Not Found");
        log.error("Product not found", ex);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiError> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Category Not Found");
        log.error("Category not found", ex);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex) {
        String errors = Arrays.toString(ex.getStackTrace());
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        log.error("Internal server error", ex);
        log.error(errors);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
