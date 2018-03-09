package com.example.product.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatedRuleException extends RuntimeException {
    public DuplicatedRuleException(Long originalRuleId) {
        super("Provided rule is already defined with id=" + originalRuleId);
    }
}
