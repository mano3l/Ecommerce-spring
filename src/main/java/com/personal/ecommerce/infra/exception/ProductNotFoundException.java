package com.personal.ecommerce.infra.exception;

public class ProductNotFoundException extends RuntimeException {

        public ProductNotFoundException(String message) {
            super(message);
        }

}
