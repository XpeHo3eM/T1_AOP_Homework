package ru.t1.java.demo.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.dto.account.NewAccountDto;
import ru.t1.java.demo.service.AccountService;

import java.util.List;

@RequiredArgsConstructor
@Component
public class KafkaAccountConsumer {

    private final AccountService accountService;

    @KafkaListener(topics = {"${t1.kafka.topic.account}"},
            containerFactory = "kafkaAccountListenerContainerFactory")
    public void listener(@Payload List<NewAccountDto> newAccountDtos,
                         Acknowledgment ack) {
        try {
            newAccountDtos.forEach(accountService::create);
        } finally {
            ack.acknowledge();
        }
    }
}
