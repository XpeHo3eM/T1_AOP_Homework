package ru.t1.java.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.annotation.LogDataSourceException;
import ru.t1.java.demo.model.Client;
import ru.t1.java.demo.repository.ClientRepository;
import ru.t1.java.demo.service.ClientService;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository repository;

    @Override
    @LogDataSourceException
    public UUID getUUIDbyId(Long clientId) {
        return getClientOrThrowException(clientId).getClientId();
    }

    private Client getClientOrThrowException(Long clientId) {
        return repository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Клиент с id = %d не найден", clientId)));
    }
}
