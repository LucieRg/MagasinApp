package org.example.service;

import org.example.entity.Client;
import org.example.repository.ClientRepository;

import java.util.List;

public class ClientService {
    private static ClientRepository clientRepository = new ClientRepository();

    public boolean addClient(Client client) {
        return clientRepository.save(client);
    }

    public boolean updateClient(Client client) {
        return clientRepository.update(client);
    }

    public boolean deleteClient(Client client) {
        return clientRepository.delete(client);
    }

    public Client findClientById(long id) {
        return clientRepository.findById(Client.class,id);
    }

    public static List<Client> findAllClient() {
        return clientRepository.findAll(Client.class);
    }
}
