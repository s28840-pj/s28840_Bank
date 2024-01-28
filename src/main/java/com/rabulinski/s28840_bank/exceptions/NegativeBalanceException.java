package com.rabulinski.s28840_bank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NegativeBalanceException extends RuntimeException {
    public NegativeBalanceException() {
        super("Account balance cannot be less than 0");
    }
}
