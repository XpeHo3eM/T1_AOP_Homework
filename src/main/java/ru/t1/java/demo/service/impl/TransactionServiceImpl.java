package ru.t1.java.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.t1.java.demo.annotation.LogDataSourceException;
import ru.t1.java.demo.dto.transaction.NewTransactionDto;
import ru.t1.java.demo.dto.transaction.TransactionDto;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.repository.AccountRepository;
import ru.t1.java.demo.repository.ClientRepository;
import ru.t1.java.demo.repository.TransactionRepository;
import ru.t1.java.demo.service.TransactionService;
import ru.t1.java.demo.util.TransactionMapper;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.List;
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

        return transactionMapper.toDto(transactionRepository.save(transactionMapper.toTransaction(newTransactionDto)));
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
