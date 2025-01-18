package ru.t1.java.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.java.demo.dto.account.AccountDto;
import ru.t1.java.demo.dto.account.NewAccountDto;
import ru.t1.java.demo.dto.account.UpdatedAccountDto;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.repository.AccountRepository;
import ru.t1.java.demo.service.AccountService;
import ru.t1.java.demo.util.AccountMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private AccountMapper mapper;

    @Override
    public AccountDto create(NewAccountDto newAccountDto) {
        return mapper.toDto(accountRepository.save(mapper.toAccount(newAccountDto)));
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto getById(Long accountId) {
        return accountRepository.findById(accountId)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Счет с id = %d не найден", accountId)));
    }

    @Override
    public AccountDto update(UpdatedAccountDto updatedAccountDto) {
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
    public void delete(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new EntityNotFoundException(String.format("Счет с id = %d не найден", accountId));
        }

        accountRepository.deleteById(accountId);
    }

    @Override
    public Collection<AccountDto> getAll(Long clientId) {
        return accountRepository.findAllByClientId(clientId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
