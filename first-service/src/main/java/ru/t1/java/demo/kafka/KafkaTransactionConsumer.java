package ru.t1.java.demo.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.dto.account.AccountDto;
import ru.t1.java.demo.dto.transaction.NewTransactionDto;
import ru.t1.java.demo.dto.TransactionAcceptDto;
import ru.t1.java.demo.dto.transaction.TransactionDto;
import ru.t1.java.demo.service.AccountService;
import ru.t1.java.demo.service.ClientService;
import ru.t1.java.demo.service.TransactionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class KafkaTransactionConsumer {
    private final ClientService clientService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final KafkaTransactionProducer producer;

    @KafkaListener(topics = {"${t1.kafka.topic.transactions.default}"},
            containerFactory = "kafkaTransactionListenerContainerFactory")
    public void listener(@Payload List<NewTransactionDto> newTransactionDtos,
                         Acknowledgment ack) {
        try {
            newTransactionDtos.stream()
                    .filter(newTransactionDto -> accountService.isOpenAccount(newTransactionDto.getAccountId()))
                    .forEach(newTransactionDto -> {
                        TransactionDto transactionFromDb = transactionService.create(newTransactionDto);
                        AccountDto accountFromDb = accountService.updateAccountBalance(newTransactionDto);
                        UUID clientUUID = clientService.getUUIDbyId(newTransactionDto.getClientId());

                        producer.send(TransactionAcceptDto.builder()
                                .clientId(clientUUID)
                                .accountId(accountFromDb.getAccountId())
                                .transactionId(transactionFromDb.getTransactionId())
                                .transactionAmount(newTransactionDto.getAmount())
                                .accountBalance(accountFromDb.getBalance())
                                .timestamp(LocalDateTime.now())
                                .build());
                    });
        } finally {
            ack.acknowledge();
        }
    }
}
