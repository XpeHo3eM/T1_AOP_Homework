package ru.t1.java.demo.dto.account;

import lombok.Builder;
import lombok.Value;
import ru.t1.java.demo.model.AccountType;

/**
 * DTO for {@link ru.t1.java.demo.model.Account}
 */
@Value
@Builder(toBuilder = true)
public class AccountDto {
    Long id;
    Long clientId;
    AccountType type;
    Double balance;
}
