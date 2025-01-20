package ru.t1.java.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.t1.java.demo.aop.LogMyException;
import ru.t1.java.demo.dto.account.AccountDto;
import ru.t1.java.demo.dto.account.NewAccountDto;
import ru.t1.java.demo.dto.account.UpdatedAccountDto;
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
@Transactional
@Validated
public class AccountServiceImpl implements AccountService {
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final AccountMapper mapper;

    @Override
    @LogMyException
    public AccountDto create(@Valid NewAccountDto newAccountDto) {
        if (!clientRepository.existsById(newAccountDto.getClientId())) {
            throw new EntityNotFoundException(String.format("Клиента с id = %d не существует",
                    newAccountDto.getClientId()));
        }

        return mapper.toDto(accountRepository.save(mapper.toAccount(newAccountDto)));
    }

    @Override
    @Transactional(readOnly = true)
    @LogMyException
    public AccountDto getById(@Valid @Positive Long accountId) {
        return accountRepository.findById(accountId)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Счет с id = %d не найден", accountId)));
    }

    @Override
    @LogMyException
    public AccountDto update(@Valid UpdatedAccountDto updatedAccountDto) {
        Long accountId = updatedAccountDto.getAccountId();
        Long clientId = updatedAccountDto.getClientId();

        Account accountFromDb = accountRepository.findByIdAndClientId(accountId, clientId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Пользователь с id = %d не имеет счета с id = %d", clientId, accountId)));

        if (updatedAccountDto.getBalance() != null) {
            accountFromDb.setBalance(updatedAccountDto.getBalance());
        }
        if (updatedAccountDto.getType() != null) {
            accountFromDb.setType(updatedAccountDto.getType());
        }

        return mapper.toDto(accountFromDb);
    }

    @Override
    @LogMyException
    public void delete(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new EntityNotFoundException(String.format("Счет с id = %d не найден", accountId));
        }

        accountRepository.deleteById(accountId);
    }

    @Override
    @LogMyException
    public Collection<AccountDto> getAll(Long clientId) {
        return accountRepository.findAllByClientId(clientId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
