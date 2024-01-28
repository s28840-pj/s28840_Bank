package com.rabulinski.s28840_bank.controllers;

import com.rabulinski.s28840_bank.models.Client;
import com.rabulinski.s28840_bank.models.Currency;
import com.rabulinski.s28840_bank.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;

    @PostMapping()
    public ResponseEntity<Client> register(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String pesel, @RequestParam Double balance, @RequestParam Currency currency) {
        var client = clientService.register(pesel, firstName, lastName, balance, currency);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable Integer id) {
        var client = clientService.findById(id);
        return ResponseEntity.ok(client);
    }

    @GetMapping()
    public ResponseEntity<List<Client>> clients(@RequestParam(required = false) Double minBalance) {
        var clients = clientService.allClientsFiltered(minBalance);
        return ResponseEntity.ok(clients);
    }
}
