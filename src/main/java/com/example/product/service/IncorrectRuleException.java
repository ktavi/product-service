package com.example.product.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class IncorrectRuleException extends RuntimeException {
    public IncorrectRuleException(String message) {
        super(message);
    }
}
