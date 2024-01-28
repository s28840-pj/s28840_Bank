package com.rabulinski.s28840_bank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PeselValidationException extends RuntimeException {
    private PeselValidationException(String reason) {
        super(reason);
    }

    public static PeselValidationException BadLength() {
        return new PeselValidationException("PESEL must have 11 digits");
    }

    public static PeselValidationException InvaidCharacter() {
        return new PeselValidationException("PESEL can only contain digits 0 through 9");
    }
}
