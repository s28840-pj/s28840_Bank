package com.rabulinski.s28840_bank.repositories;

import com.rabulinski.s28840_bank.models.Client;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClientRepository {
    @Getter
    private Integer nextClientId = 1;
    @Getter
    private List<Client> clients = new ArrayList<>();

    public void addClient(Client client) {
        clients.add(client);
        nextClientId += 1;
    }

    public void purge() {
        clients = new ArrayList<>();
        nextClientId = 1;
    }
}
