package ru.t1.java.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.java.demo.dto.client.ClientDto;
import ru.t1.java.demo.kafka.KafkaClientProducer;
import ru.t1.java.demo.service.ClientService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
@Slf4j
public class ClientController {

    private final ClientService clientService;
    private final KafkaClientProducer producer;

    @GetMapping
    public void doSomething(@RequestBody ClientDto clientDto) {
        producer.send(1L);
    }
}
