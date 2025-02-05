package ru.t1.java.demo.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.t1.java.demo.dto.account.AccountDto;
import ru.t1.java.demo.dto.account.NewAccountDto;
import ru.t1.java.demo.model.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "balance", constant = "0.0")
    Account toAccount(NewAccountDto newAccountDto);

    AccountDto toDto(Account account);
}
