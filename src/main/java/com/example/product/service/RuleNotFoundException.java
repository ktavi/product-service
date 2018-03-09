package com.example.product.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RuleNotFoundException extends RuntimeException {
    public RuleNotFoundException(Long id) {
        super("Rule with id=" + id + " not found");
    }
}
