package ru.t1.java.demo.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.dto.transaction.TransactionAcceptDto;
import ru.t1.java.demo.dto.transaction.TransactionResultDto;
import ru.t1.java.demo.enums.TransactionStatus;
import ru.t1.java.demo.mapper.TransactionMapper;
import ru.t1.java.demo.service.TransactionService;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class KafkaTransactionConsumer {
    private final TransactionService transactionService;
    private final KafkaTransactionProducer producer;
    private final TransactionMapper mapper;

    @KafkaListener(topics = {"${t1.kafka.topic.transactions.accepted}"},
            containerFactory = "kafkaTransactionAcceptListenerContainerFactory")
    public void listener(@Payload List<TransactionAcceptDto> transactionAcceptDtos,
                         Acknowledgment ack) {
        try {
            Set<TransactionResultDto> resultDtos = new HashSet<>();

            for (TransactionAcceptDto transactionAcceptDto : transactionAcceptDtos) {
                Collection<TransactionResultDto> blockedTransactions =
                        transactionService.checkBlockedTransactionByLimit(transactionAcceptDto);

                if (blockedTransactions.isEmpty()) {
                    TransactionResultDto transaction = mapper.toResult(transactionAcceptDto);

                    if (transactionAcceptDto.getAccountBalance() < 0) {
                        transaction = transaction.toBuilder()
                                .status(TransactionStatus.REJECTED)
                                .build();
                    } else {
                        transaction = transaction.toBuilder()
                                .status(TransactionStatus.ACCEPTED)
                                .build();
                    }

                    resultDtos.add(transaction);
                } else {
                    resultDtos.addAll(blockedTransactions);
                }
            }

            for (TransactionResultDto resultDto : resultDtos) {
                producer.send(resultDto);
            }
        } finally {
            ack.acknowledge();
        }
    }
}
