package ru.t1.java.firstService.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.java.general.dto.account.AccountDto;
import ru.t1.java.firstService.dto.transaction.NewTransactionDto;
import ru.t1.java.general.dto.transaction.TransactionAcceptDto;
import ru.t1.java.general.dto.transaction.TransactionDto;
import ru.t1.java.general.dto.transaction.TransactionResultDto;
import ru.t1.java.general.enums.TransactionStatus;
import ru.t1.java.firstService.service.AccountService;
import ru.t1.java.firstService.service.ClientService;
import ru.t1.java.firstService.service.TransactionService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class KafkaTransactionConsumer {
    private final ClientService clientService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final KafkaTransactionProducer producer;

    @KafkaListener(topics = {"${t1.kafka.topic.transactions.default}"},
            containerFactory = "kafkaTransactionListenerContainerFactory")
    public void transactionListener(@Payload List<NewTransactionDto> newTransactionDtos,
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

    @KafkaListener(topics = {"${t1.kafka.topic.transactions.result}"},
            containerFactory = "kafkaTransactionResultListenerContainerFactory")
    public void transactionResultListener(@Payload List<TransactionResultDto> transactionResultDtos,
                                          Acknowledgment ack) {
        try {
            actionWithAcceptedTransactions(transactionResultDtos);
            actionWithBlockedTransactions(transactionResultDtos);
            actionWithRejectedTransactions(transactionResultDtos);
        } finally {
            ack.acknowledge();
        }
    }

    private List<UUID> getTransactionUUIDsByStatus(List<TransactionResultDto> transactions, TransactionStatus status) {
        return transactions.stream()
                .filter(dto -> dto.getStatus().equals(status))
                .map(TransactionResultDto::getTransactionId)
                .collect(Collectors.toList());
    }

    private void actionWithAcceptedTransactions(List<TransactionResultDto> dtos) {
        List<UUID> acceptedTransactionUUIDs = getTransactionUUIDsByStatus(dtos, TransactionStatus.ACCEPTED);

        if (acceptedTransactionUUIDs.isEmpty()) {
            return;
        }

        transactionService.acceptTransactions(acceptedTransactionUUIDs);
    }

    private void actionWithBlockedTransactions(List<TransactionResultDto> dtos) {
        List<UUID> blockedTransactionUUIDs = getTransactionUUIDsByStatus(dtos, TransactionStatus.BLOCKED);

        if (blockedTransactionUUIDs.isEmpty()) {
            return;
        }

        Collection<TransactionDto> blockedTransactions = transactionService.blockTransactions(blockedTransactionUUIDs);

        Map<Long, Double> accountIdAndBlockedAmount = blockedTransactions.stream()
                .collect(Collectors.groupingBy(
                        TransactionDto::getAccountId,
                        Collectors.summingDouble(TransactionDto::getAmount)));

        accountService.blockAccounts(accountIdAndBlockedAmount);
    }

    private void actionWithRejectedTransactions(List<TransactionResultDto> dtos) {
        List<UUID> rejectedTransactionUUIDs = getTransactionUUIDsByStatus(dtos, TransactionStatus.REJECTED);

        if (rejectedTransactionUUIDs.isEmpty()) {
            return;
        }

        Collection<TransactionDto> rejectedTransactions = transactionService.rejectTransactions(rejectedTransactionUUIDs);

        Map<Long, Double> accountIdAndRejectedAmount = rejectedTransactions.stream()
                .collect(Collectors.groupingBy(
                        TransactionDto::getAccountId,
                        Collectors.summingDouble(TransactionDto::getAmount)));

        accountService.rejectAccounts(accountIdAndRejectedAmount);
    }
}
