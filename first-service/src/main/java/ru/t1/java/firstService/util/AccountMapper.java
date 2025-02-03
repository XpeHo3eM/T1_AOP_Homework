package ru.t1.java.firstService.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.t1.java.firstService.dto.account.NewAccountDto;
import ru.t1.java.firstService.model.Account;
import ru.t1.java.general.dto.account.AccountDto;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "balance", constant = "0.0")
    @Mapping(target = "status", constant = "OPEN")
    @Mapping(target = "accountId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "frozenAmount", constant = "0")
    Account toAccount(NewAccountDto newAccountDto);

    AccountDto toDto(Account account);
}
