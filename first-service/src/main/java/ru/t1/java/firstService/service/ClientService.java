package ru.t1.java.firstService.service;


import ru.t1.java.firstService.model.Client;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ClientService {
    List<Client> parseJson() throws IOException;

    UUID getUUIDbyId(Long clientId);
}
