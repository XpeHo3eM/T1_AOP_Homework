package ru.t1.java.demo.service;

import ru.t1.java.demo.dto.account.AccountDto;
import ru.t1.java.demo.dto.account.NewAccountDto;
import ru.t1.java.demo.dto.account.UpdatedAccountDto;

import java.util.Collection;

public interface AccountService {
    AccountDto create(NewAccountDto newAccountDto);

    AccountDto getById(Long clientId, Long accountId);

    AccountDto update(UpdatedAccountDto updatedAccountDto);

    void delete(Long clientId, Long accountId);

    Collection<AccountDto> getAll(Long clientId);
}
