package com.rabulinski.s28840_bank.services;

import com.rabulinski.s28840_bank.exceptions.ClientNotFoundException;
import com.rabulinski.s28840_bank.exceptions.MissingValueException;
import com.rabulinski.s28840_bank.exceptions.NegativeBalanceException;
import com.rabulinski.s28840_bank.exceptions.PeselValidationException;
import com.rabulinski.s28840_bank.models.Client;
import com.rabulinski.s28840_bank.models.Currency;
import com.rabulinski.s28840_bank.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public Client register(String pesel, String firstName, String lastName, Double initialBalance, Currency currency) {
        if (pesel == null) throw new MissingValueException("pesel");
        if (firstName == null) throw new MissingValueException("firstName");
        if (lastName == null) throw new MissingValueException("lastName");
        if (initialBalance == null) throw new MissingValueException("initialBalance");
        if (currency == null) throw new MissingValueException("currency");

        if (!pesel.chars().allMatch((c) -> c >= '0' && c <= '9')) throw PeselValidationException.InvaidCharacter();
        if (pesel.length() != 11) throw PeselValidationException.BadLength();
        if (initialBalance < 0) throw new NegativeBalanceException();

        var nextId = clientRepository.getNextClientId();

        var client = new Client(nextId, pesel, firstName, lastName, initialBalance, currency);
        clientRepository.addClient(client);
        return client;
    }

    public Client findById(Integer id) {
        return clientRepository.getClients().stream().filter((client) -> client.getId() == id).findFirst().orElseThrow(() -> new ClientNotFoundException(id));
    }

    public List<Client> allClients() {
        return clientRepository.getClients();
    }

    public List<Client> allClientsFiltered(Double minBalance) {
        var clients = allClients().stream();

        if (minBalance != null) clients = clients.filter(client -> client.getBalance() >= minBalance);

        return clients.toList();
    }
}
