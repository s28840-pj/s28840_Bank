package com.rabulinski.s28840_bank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MissingValueException extends RuntimeException {
    public MissingValueException(String field) {
        super("Value for field " + field + " is missing");
    }
}
