package ru.t1.java.demo.service;

import ru.t1.java.demo.dto.transaction.NewTransactionDto;
import ru.t1.java.demo.dto.transaction.TransactionDto;

import java.util.Collection;

public interface TransactionService {
    Collection<TransactionDto> getAll(Long clientId);

    TransactionDto create(NewTransactionDto newTransactionDto);

    TransactionDto getById(Long transactionId);
}
