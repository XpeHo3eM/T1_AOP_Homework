package ru.t1.java.demo.service;

import ru.t1.java.demo.dto.transaction.TransactionAcceptDto;
import ru.t1.java.demo.dto.transaction.TransactionResultDto;

import java.util.Collection;

public interface TransactionService {
    Collection<TransactionResultDto> checkBlockedTransactionByLimit(TransactionAcceptDto transactionAcceptDto);
}
