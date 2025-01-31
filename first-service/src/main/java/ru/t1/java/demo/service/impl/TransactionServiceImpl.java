package ru.t1.java.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.t1.java.demo.annotation.LogDataSourceException;
import ru.t1.java.demo.dto.transaction.NewTransactionDto;
import ru.t1.java.demo.dto.transaction.TransactionDto;
import ru.t1.java.demo.enums.TransactionStatus;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.repository.AccountRepository;
import ru.t1.java.demo.repository.ClientRepository;
import ru.t1.java.demo.repository.TransactionRepository;
import ru.t1.java.demo.service.TransactionService;
import ru.t1.java.demo.util.TransactionMapper;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
@LogDataSourceException
public class TransactionServiceImpl implements TransactionService {
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    @LogDataSourceException
    public Collection<TransactionDto> getAll(@Valid @Positive Long clientId) {
        assertClientExists(clientId);

        List<Long> accountIds = accountRepository.findAllByClientId(clientId).stream()
                .map(Account::getId)
                .collect(Collectors.toList());

        return transactionRepository.findAllByAccountIdIn(accountIds).stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @LogDataSourceException
    public Collection<TransactionDto> getAllByAccount(Long clientId, Long accountId) {
        assertClientExists(clientId);
        assertAccountExists(accountId);

        return transactionRepository.findAllByAccountId(accountId).stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @LogDataSourceException
    public TransactionDto create(@Valid NewTransactionDto newTransactionDto) {
        assertClientExists(newTransactionDto.getClientId());
        assertAccountExists(newTransactionDto.getAccountId());

        Transaction transaction = transactionMapper.toTransaction(newTransactionDto).toBuilder()
                .transactionId(UUID.randomUUID())
                .status(TransactionStatus.REQUESTED)
                .build();

        return transactionMapper.toDto(transactionRepository.save(transaction));
    }

    @Override
    @LogDataSourceException
    public TransactionDto getById(@Valid @Positive Long clientId,
                                  @Valid @Positive Long transactionId) {
        assertClientExists(clientId);

        return transactionRepository.findById(transactionId)
                .map(transactionMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Транзакция с id = %d не найдена", transactionId)));
    }

    @Override
    @Transactional
    @LogDataSourceException
    public void acceptTransactions(Collection<UUID> transactionIds) {
        updateTransactionsStatus(transactionIds, TransactionStatus.ACCEPTED);
    }

    @Override
    @Transactional
    @LogDataSourceException
    public Collection<TransactionDto> blockTransactions(Collection<UUID> transactionIds) {
        if (transactionIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<Transaction> transactions = transactionRepository.findAllByTransactionIdInAndStatusNot(transactionIds,
                        TransactionStatus.BLOCKED).stream()
                .toList();

        for (Transaction transaction : transactions) {
            transaction.setStatus(TransactionStatus.BLOCKED);
        }

        return transactions.stream()
                .map(transactionMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    @LogDataSourceException
    public Collection<TransactionDto> rejectTransactions(Collection<UUID> transactionIds) {
        return updateTransactionsStatus(transactionIds, TransactionStatus.REJECTED).stream()
                .map(transactionMapper::toDto)
                .toList();
    }

    private List<Transaction> updateTransactionsStatus(Collection<UUID> transactionIds, TransactionStatus status) {
        if (transactionIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<Transaction> transactions = getTransactionsByUUID(transactionIds);

        for (Transaction transaction : transactions) {
            transaction.setStatus(status);
        }

        return transactions;
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

    private List<Transaction> getTransactionsByUUID(Collection<UUID> uuids) {
        return transactionRepository.findAllByTransactionIdIn(uuids).stream()
                .toList();
    }
}
