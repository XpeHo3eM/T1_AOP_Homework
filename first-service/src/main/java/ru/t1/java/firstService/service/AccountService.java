package ru.t1.java.firstService.service;

import ru.t1.java.general.dto.account.AccountDto;
import ru.t1.java.firstService.dto.account.NewAccountDto;
import ru.t1.java.firstService.dto.account.UpdatedAccountDto;
import ru.t1.java.firstService.dto.transaction.NewTransactionDto;

import java.util.Collection;
import java.util.Map;

public interface AccountService {
    AccountDto create(NewAccountDto newAccountDto);

    AccountDto getById(Long clientId, Long accountId);

    AccountDto update(UpdatedAccountDto updatedAccountDto);

    void delete(Long clientId, Long accountId);

    Collection<AccountDto> getAll(Long clientId);

    boolean isOpenAccount(Long accountId);

    AccountDto updateAccountBalance(NewTransactionDto newTransactionDto);

    void blockAccounts(Map<Long, Double> accountIds);
    void rejectAccounts(Map<Long, Double> accountIds);
}
