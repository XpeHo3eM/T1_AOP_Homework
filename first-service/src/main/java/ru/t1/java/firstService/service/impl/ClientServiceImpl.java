package ru.t1.java.firstService.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.t1.java.firstService.model.Client;
import ru.t1.java.firstService.repository.ClientRepository;
import ru.t1.java.firstService.service.ClientService;
import ru.t1.java.firstService.util.ClientMapper;
import ru.t1.java.general.annotation.LogDataSourceException;
import ru.t1.java.general.dto.client.ClientDto;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository repository;

    @PostConstruct
    void init() {
        try {
            List<Client> clients = parseJson();
        } catch (IOException e) {
            log.error("Ошибка во время обработки записей", e);
        }
//        repository.saveAll(clients);
    }

    @Override
//    @LogExecution
//    @Track
//    @HandlingResult
    public List<Client> parseJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        //ClientDto[] clients = mapper.readValue(new File("src/main/resources/MOCK_DATA.json"), ClientDto[].class);

//        return Arrays.stream(clients)
//                .map(ClientMapper::toEntity)
//                .collect(Collectors.toList());
        return Collections.emptyList();
    }

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
