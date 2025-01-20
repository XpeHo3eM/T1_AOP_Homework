package ru.t1.java.demo.util;

import org.mapstruct.Mapper;
import ru.t1.java.demo.dto.account.AccountDto;
import ru.t1.java.demo.dto.account.NewAccountDto;
import ru.t1.java.demo.model.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toAccount(NewAccountDto newAccountDto);

    AccountDto toDto(Account account);
}
