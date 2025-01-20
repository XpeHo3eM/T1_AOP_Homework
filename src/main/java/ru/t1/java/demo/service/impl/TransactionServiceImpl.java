package ru.t1.java.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.t1.java.demo.aop.LogMyException;
import ru.t1.java.demo.dto.transaction.NewTransactionDto;
import ru.t1.java.demo.dto.transaction.TransactionDto;
import ru.t1.java.demo.repository.AccountRepository;
import ru.t1.java.demo.repository.TransactionRepository;
import ru.t1.java.demo.service.TransactionService;
import ru.t1.java.demo.util.TransactionMapper;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
@LogMyException
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;


    @Override
    @LogMyException
    public Collection<TransactionDto> getAll(@Valid @Positive Long accountId) {
        assertAccountExists(accountId);

        return transactionRepository.findAllByAccountId(accountId).stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @LogMyException
    public TransactionDto create(@Valid NewTransactionDto newTransactionDto) {
        assertAccountExists(newTransactionDto.getAccountId());

        return transactionMapper.toDto(transactionRepository.save(transactionMapper.toTransaction(newTransactionDto)));
    }

    @Override
    @LogMyException
    public TransactionDto getById(@Valid @Positive Long transactionId) {
        return transactionRepository.findById(transactionId)
                .map(transactionMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Транзакция с id = %d не найдена", transactionId)));
    }

    private void assertAccountExists(Long accountId) throws EntityNotFoundException {
        if (!accountRepository.existsById(accountId)) {
            throw new EntityNotFoundException(String.format("Счет с id = %d не найден", accountId));
        }
    }
}
