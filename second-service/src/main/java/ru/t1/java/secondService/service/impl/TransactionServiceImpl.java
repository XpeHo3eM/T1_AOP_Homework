package ru.t1.java.secondService.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.t1.java.general.annotation.LogDataSourceException;
import ru.t1.java.general.dto.transaction.TransactionAcceptDto;
import ru.t1.java.general.dto.transaction.TransactionResultDto;
import ru.t1.java.general.enums.TransactionStatus;
import ru.t1.java.secondService.kafka.DefaultKafkaConfig;
import ru.t1.java.secondService.mapper.TransactionMapper;
import ru.t1.java.secondService.model.Account;
import ru.t1.java.secondService.model.Transaction;
import ru.t1.java.secondService.repository.AccountRepository;
import ru.t1.java.secondService.repository.TransactionRepository;
import ru.t1.java.secondService.service.TransactionService;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final DefaultKafkaConfig config;

    @Override
    @LogDataSourceException
    public Collection<TransactionResultDto> checkBlockedTransactionByLimit(TransactionAcceptDto transactionAcceptDto) {
        Optional<Account> accountFromDb = accountRepository.findByAccountId(transactionAcceptDto.getAccountId());
        Account account;

        if (accountFromDb.isPresent()) {
            account = accountFromDb.get();
        } else {
            return Collections.emptyList();
        }

        List<Transaction> lastNTransactions =
                transactionRepository.findAllByAccountIdWithLimit(account.getId(),
                                config.getTransactionCountPerTime()).stream()
                        .filter(transaction -> transaction.getTimestamp().isAfter(
                                transactionAcceptDto.getTimestamp()
                                        .minusNanos((long) (config.getTransactionTimeIntervalMs() * 1e6))))
                        .toList();

        Collection<TransactionResultDto> blockedTransactions = new ArrayList<>();

        if (lastNTransactions.size() >= config.getTransactionCountPerTime() - 1) {
            for (Transaction transaction : lastNTransactions) {
                if (transaction.getStatus().equals(TransactionStatus.BLOCKED)) {
                    continue;
                }

                blockedTransactions.add(TransactionResultDto.builder()
                        .status(TransactionStatus.BLOCKED)
                        .transactionId(transaction.getTransactionId())
                        .accountId(account.getAccountId())
                        .build());
            }

            blockedTransactions.add(transactionMapper.toResult(transactionAcceptDto).toBuilder()
                    .status(TransactionStatus.BLOCKED)
                    .build());
        }

        return blockedTransactions;
    }
}
