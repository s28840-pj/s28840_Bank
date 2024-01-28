package com.rabulinski.s28840_bank.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Client {
    private final Integer id;
    private final String pesel;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private Double balance;
    @NonNull
    private Currency currency;
}
