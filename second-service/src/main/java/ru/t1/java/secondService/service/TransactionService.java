package ru.t1.java.secondService.service;

import ru.t1.java.general.dto.transaction.TransactionAcceptDto;
import ru.t1.java.general.dto.transaction.TransactionResultDto;

import java.util.Collection;

public interface TransactionService {
    Collection<TransactionResultDto> checkBlockedTransactionByLimit(TransactionAcceptDto transactionAcceptDto);
}
