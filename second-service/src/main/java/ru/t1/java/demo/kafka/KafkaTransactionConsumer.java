package ru.t1.java.demo.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.dto.TransactionAcceptDto;
import ru.t1.java.demo.dto.TransactionResultDto;
import ru.t1.java.demo.mapper.TransactionMapper;

import java.util.List;

@RequiredArgsConstructor
@Component
public class KafkaTransactionConsumer {
    //    private final ClientService clientService;
//    private final AccountService accountService;
//    private final TransactionService transactionService;
    private final KafkaTransactionProducer producer;
    private final TransactionMapper mapper;

    @KafkaListener(topics = {"${t1.kafka.topic.transactions.accepted}"},
            containerFactory = "kafkaTransactionAcceptListenerContainerFactory")
    public void listener(@Payload List<TransactionAcceptDto> transactionAcceptDtos,
                         Acknowledgment ack) {
        try {
            List<TransactionResultDto> resultDtos = transactionAcceptDtos.stream()
                    .map(mapper::toResult)
                    .toList();


        } finally {
            ack.acknowledge();
        }
    }
}
