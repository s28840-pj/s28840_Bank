package com.rabulinski.s28840_bank.controllers;

import com.rabulinski.s28840_bank.S28840BankApplication;
import com.rabulinski.s28840_bank.models.Client;
import com.rabulinski.s28840_bank.models.Currency;
import com.rabulinski.s28840_bank.repositories.ClientRepository;
import com.rabulinski.s28840_bank.services.ClientService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@AllArgsConstructor
final class ClientBody {
    public String pesel;
    public String firstName;
    public String lastName;
    public Double balance;
    public Currency currency;
}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = S28840BankApplication.class)
@AutoConfigureWebTestClient
public class ClientControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientService clientService;

    @BeforeEach
    void Setup() {
        clientRepository.purge();
    }

    @Test
    void ShouldReturnAllAccounts() {
        var client1 = assertDoesNotThrow(() -> clientService.register("11111111111", "John", "Doe", 0., Currency.PLN));
        var client2 = assertDoesNotThrow(() -> clientService.register("22222222222", "Jan", "Kowalski", 0., Currency.USD));

        webTestClient.get().uri("/client").exchange().expectStatus().isOk().expectBodyList(Client.class).hasSize(2).contains(client1, client2);
    }

    @Test
    void ShouldReturnFilteredAccounts() {
        var client1 = assertDoesNotThrow(() -> clientService.register("11111111111", "John", "Doe", 0., Currency.PLN));
        var client2 = assertDoesNotThrow(() -> clientService.register("22222222222", "Jan", "Kowalski", 100., Currency.USD));

        webTestClient.get().uri(builder -> builder.path("/client").queryParam("minBalance", 50.0).build()).exchange().expectStatus().isOk().expectBodyList(Client.class).hasSize(1).contains(client2);
    }

    @Test
    void ShouldThrowOnInvalidAccountId() {
        webTestClient.get().uri("/client/1").exchange().expectStatus().isNotFound();
    }

    @Test
    void ShouldThrowOnInvalidPeselLength() {
        var client = new ClientBody("111111111111", "John", "Doe", 0., Currency.USD);
        webTestClient.post().uri("/client").bodyValue(client).exchange().expectStatus().isBadRequest();
    }

    @Test
    void ShouldThrowOnInvalidPeselCharacters() {
        var client = new ClientBody("FOOBAR", "John", "Doe", 0., Currency.USD);
        webTestClient.post().uri("/client").bodyValue(client).exchange().expectStatus().isBadRequest();
    }

    @Test
    void ShouldThrowOnNegativeAccountBalance() {
        var client = new ClientBody("11111111111", "John", "Doe", -5., Currency.USD);
        webTestClient.post().uri("/client").bodyValue(client).exchange().expectStatus().isBadRequest();
    }

    @Test
    void ShouldThrowOnMissingData() {
        var client = new ClientBody("11111111111", null, "Doe", 5., Currency.USD);
        webTestClient.post().uri("/client").bodyValue(client).exchange().expectStatus().isBadRequest();
    }
}
