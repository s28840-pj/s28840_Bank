package com.rabulinski.s28840_bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// @SpringBootApplication
// IntelliJ nie wykrywa poprawnie Beansów jeśli używam SpringBootApplication,
// dlatego daję te 3 atrybuty osobno.
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class S28840BankApplication {

    public static void main(String[] args) {
        SpringApplication.run(S28840BankApplication.class, args);
    }

}
