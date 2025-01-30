package ru.t1.java.demo.mapper;

import org.mapstruct.Mapper;
import ru.t1.java.demo.dto.account.AccountDto;
import ru.t1.java.demo.model.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto toDto(Account account);
}
