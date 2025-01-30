package ru.t1.java.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.t1.java.demo.annotation.LogDataSourceException;
import ru.t1.java.demo.config.DefaultKafkaConfig;
import ru.t1.java.demo.dto.transaction.TransactionAcceptDto;
import ru.t1.java.demo.dto.transaction.TransactionResultDto;
import ru.t1.java.demo.enums.TransactionStatus;
import ru.t1.java.demo.mapper.TransactionMapper;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.repository.AccountRepository;
import ru.t1.java.demo.repository.ClientRepository;
import ru.t1.java.demo.repository.TransactionRepository;
import ru.t1.java.demo.service.TransactionService;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class TransactionServiceImpl implements TransactionService {
    private final ClientRepository clientRepository;
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

    private void assertAccountExists(Long accountId) throws EntityNotFoundException {
        if (!accountRepository.existsById(accountId)) {
            throw new EntityNotFoundException(String.format("Счет с id = %d не найден", accountId));
        }
    }

    private void assertClientExists(Long clientId) throws EntityNotFoundException {
        if (!clientRepository.existsById(clientId)) {
            throw new EntityNotFoundException(String.format("Пользователь с id = %d не найден", clientId));
        }
    }
}
