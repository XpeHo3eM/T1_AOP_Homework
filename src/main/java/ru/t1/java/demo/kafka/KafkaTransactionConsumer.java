package ru.t1.java.demo.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.dto.transaction.NewTransactionDto;
import ru.t1.java.demo.service.TransactionService;

import java.util.List;

@RequiredArgsConstructor
@Component
public class KafkaTransactionConsumer {

    private final TransactionService transactionService;

    @KafkaListener(topics = {"${t1.kafka.topic.transaction}"},
            containerFactory = "kafkaTransactionListenerContainerFactory")
    public void listener(@Payload List<NewTransactionDto> newTransactionDtos,
                         Acknowledgment ack) {
        try {
            newTransactionDtos.forEach(transactionService::create);
        } finally {
            ack.acknowledge();
        }
    }
}
