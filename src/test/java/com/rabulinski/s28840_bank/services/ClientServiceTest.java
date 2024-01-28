package com.rabulinski.s28840_bank.services;

import com.rabulinski.s28840_bank.exceptions.ClientNotFoundException;
import com.rabulinski.s28840_bank.exceptions.MissingValueException;
import com.rabulinski.s28840_bank.exceptions.NegativeBalanceException;
import com.rabulinski.s28840_bank.exceptions.PeselValidationException;
import com.rabulinski.s28840_bank.models.Currency;
import com.rabulinski.s28840_bank.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceTest {
    private ClientRepository clientRepository;
    private ClientService clientService;

    @BeforeEach
    void Setup() {
        clientRepository = new ClientRepository();
        clientService = new ClientService(clientRepository);
    }

    @Test
    void ShouldThrowOnInvalidPeselLength() {
        assertThrows(PeselValidationException.class, () -> clientService.register("111111111111", "John", "Doe", 0., Currency.USD));
    }

    @Test
    void ShouldThrowOnInvalidPeselCharacters() {
        assertThrows(PeselValidationException.class, () -> clientService.register("FOOBAR", "John", "Doe", 0., Currency.USD));
    }

    @Test
    void ShouldThrowOnNegativeAccountBalance() {
        assertThrows(NegativeBalanceException.class, () -> clientService.register("11111111111", "John", "Doe", -2., Currency.USD));
    }

    @Test
    void ShouldThrowOnMissingData() {
        assertThrows(MissingValueException.class, () -> clientService.register(null, null, null, null, null));
    }

    @Test
    void ShouldThrowOnInvalidAccountId() {
        assertThrows(ClientNotFoundException.class, () -> clientService.findById(1));
    }

    @Test
    void ShouldCreateAccount() {
        assertDoesNotThrow(() -> clientService.register("11111111111", "John", "Doe", 0., Currency.PLN));
        assertEquals(1, clientService.allClients().size());
    }

    @Test
    void ShouldReturnAllAccounts() {
        var client1 = assertDoesNotThrow(() -> clientService.register("11111111111", "John", "Doe", 0., Currency.PLN));
        var client2 = assertDoesNotThrow(() -> clientService.register("22222222222", "Jan", "Kowalski", 0., Currency.USD));

        assertEquals(2, clientService.allClients().size());
        assertEquals(client1, clientService.allClients().get(0));
        assertEquals(client2, clientService.allClients().get(1));
    }

    @Test
    void ShouldReturnFilteredAccounts() {
        var client1 = assertDoesNotThrow(() -> clientService.register("11111111111", "John", "Doe", 0., Currency.PLN));
        var client2 = assertDoesNotThrow(() -> clientService.register("22222222222", "Jan", "Kowalski", 100., Currency.USD));

        var filteredClients = clientService.allClientsFiltered(50.);
        assertEquals(2, clientService.allClients().size());
        assertEquals(1, filteredClients.size());
        assertEquals(client2, filteredClients.get(0));
    }
}
