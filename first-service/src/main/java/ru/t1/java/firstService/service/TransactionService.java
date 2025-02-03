package ru.t1.java.firstService.service;

import ru.t1.java.firstService.dto.transaction.NewTransactionDto;
import ru.t1.java.general.dto.transaction.TransactionDto;

import java.util.Collection;
import java.util.UUID;

public interface TransactionService {
    Collection<TransactionDto> getAll(Long clientId);
    Collection<TransactionDto> getAllByAccount(Long clientId, Long accountId);

    TransactionDto create(NewTransactionDto newTransactionDto);

    TransactionDto getById(Long clientId, Long transactionId);

    void acceptTransactions(Collection<UUID> transactionIds);
    Collection<TransactionDto> blockTransactions(Collection<UUID> transactionIds);
    Collection<TransactionDto> rejectTransactions(Collection<UUID> transactionIds);
}
