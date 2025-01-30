package ru.t1.java.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.t1.java.demo.annotation.LogDataSourceException;
import ru.t1.java.demo.dto.account.AccountDto;
import ru.t1.java.demo.dto.account.NewAccountDto;
import ru.t1.java.demo.dto.account.UpdatedAccountDto;
import ru.t1.java.demo.dto.transaction.NewTransactionDto;
import ru.t1.java.demo.enums.AccountStatus;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.repository.AccountRepository;
import ru.t1.java.demo.repository.ClientRepository;
import ru.t1.java.demo.service.AccountService;
import ru.t1.java.demo.util.AccountMapper;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class AccountServiceImpl implements AccountService {
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final AccountMapper mapper;

    @Override
    @Transactional
    @LogDataSourceException
    public AccountDto create(@Valid NewAccountDto newAccountDto) {
        assertClientExists(newAccountDto.getClientId());

        return mapper.toDto(accountRepository.save(mapper.toAccount(newAccountDto)));
    }

    @Override
    @LogDataSourceException
    public AccountDto getById(@Valid @Positive Long clientId,
                              @Valid @Positive Long accountId) {
        assertClientExists(clientId);

        return mapper.toDto(getAccountOrThrowException(clientId, accountId));
    }

    @Override
    @Transactional
    @LogDataSourceException
    public AccountDto update(@Valid UpdatedAccountDto updatedAccountDto) {
        Long clientId = updatedAccountDto.getClientId();
        Long accountId = updatedAccountDto.getAccountId();

        assertClientExists(clientId);

        Account accountFromDb = getAccountOrThrowException(clientId, accountId);

        if (updatedAccountDto.getBalance() != null) {
            accountFromDb.setBalance(updatedAccountDto.getBalance());
        }
        if (updatedAccountDto.getType() != null) {
            accountFromDb.setType(updatedAccountDto.getType());
        }

        return mapper.toDto(accountFromDb);
    }

    @Override
    @Transactional
    @LogDataSourceException
    public void delete(@Valid @Positive Long clientId,
                       @Valid @Positive Long accountId) {
        assertClientExists(clientId);

        accountRepository.deleteById(accountId);
    }

    @Override
    @LogDataSourceException
    public Collection<AccountDto> getAll(@Valid @Positive Long clientId) {
        assertClientExists(clientId);

        return accountRepository.findAllByClientId(clientId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @LogDataSourceException
    public boolean isOpenAccount(@Valid @Positive Long accountId) {
        return accountRepository.existsByIdAndStatus(accountId, AccountStatus.OPEN);
    }

    @Override
    @Transactional
    @LogDataSourceException
    public AccountDto updateAccountBalance(@Valid NewTransactionDto newTransactionDto) {
        assertClientExists(newTransactionDto.getClientId());

        Account accountFromDb = getAccountOrThrowException(newTransactionDto.getClientId(),
                newTransactionDto.getAccountId());

        accountFromDb.setBalance(accountFromDb.getBalance() - newTransactionDto.getAmount());

        return mapper.toDto(accountFromDb);
    }

    private void assertClientExists(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new EntityNotFoundException(String.format("Клиент с id = %d не найден", clientId));
        }
    }

    private Account getAccountOrThrowException(Long clientId, Long accountId) {
        return accountRepository.findByIdAndClientId(accountId, clientId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Пользователь с id = %d не имеет счета с id = %d", clientId, accountId)));
    }
}
