package ru.t1.java.general.dto.account;

import lombok.Builder;
import lombok.Value;
import ru.t1.java.general.enums.AccountStatus;
import ru.t1.java.general.enums.AccountType;

import java.io.Serializable;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class AccountDto implements Serializable {
    Long id;
    Long clientId;
    AccountType type;
    Double balance;
    AccountStatus status;
    UUID accountId;
    Double frozenAmount;
}
